package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.exception.JavaWebTokenVerifyRuntimeException;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import com.tcxx.serve.wechat.model.sns.SnsUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ball/weChatAuth")
public class WeChatAuthorizeController {

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private TcUserService tcUserService;

    @Autowired
    private RedisUtil redisUtil;

    @PassTokenValidation
    @RequestMapping("/getAuthorizeUserInfoUrl")
    public Result<String> getAuthorizeUserInfoUrl(String redirectUrl) {
        log.info("redirectUrl=" + redirectUrl);
        if (StringUtils.isBlank(redirectUrl)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "redirectUrl不能为空");
        }
        Result<String> result = ResultBuild.wrapSuccess();
        result.setValue(weChatClient.sns().getOAuth2CodeUserInfoUrl(redirectUrl));
        return result;
    }

    @PassTokenValidation
    @RequestMapping("/getToken")
    public Result<String> getToken(String code) {
        log.info("code=" + code);
        if (StringUtils.isBlank(code)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "code不能为空");
        }
        // 根据code获取微信授权access_token
        SnsAccessToken snsAccessToken = weChatClient.sns().getSnsOAuth2AccessToken(code);
        // 获取微信用户信息
        SnsUser snsUser = weChatClient.sns().getSnsUser(snsAccessToken.getAccessToken(), snsAccessToken.getOpenId());
        // 创建用户 保存数据库
        String userId = tcUserService.insert(buildTcUser(snsUser, snsAccessToken));
        // accessToken、refreshToken缓存redis
        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId), snsAccessToken.getAccessToken(),  110 * 60); //110分钟
        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId), snsAccessToken.getRefreshToken(),  29 * 24 * 60 * 60); //29天
        // 生成JavaWebToken
        String token = JavaWebTokenUtil.createToken(userId, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET, JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER, JavaWebTokenUtil.TC_BALL_TOKEN_EXPIRE_HOURS);

        Result<String> result = ResultBuild.wrapSuccess();
        result.setValue(token);
        return result;
    }

    @PassTokenValidation
    @RequestMapping("/refreshToken")
    public Result<String> refreshToken(String token) {
        if (StringUtils.isBlank(token)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "token不能为空");
        }
        //获取签发者
        String issuer = JavaWebTokenUtil.getIssuer(token);
        //判断签发者是否合法
        if (!JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER.equals(issuer)){
            throw new JavaWebTokenVerifyRuntimeException(ResultCodeEnum.ERROR4501);
        }
        //获取用户id
        String userId = JavaWebTokenUtil.getAudience(token);
        //校验token
        boolean res = JavaWebTokenUtil.verifyToken(token, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET);
        if(res) { // 校验通过 说明token有效 无需刷新
            throw new JavaWebTokenVerifyRuntimeException(ResultCodeEnum.ERROR4503);
        }
        // 生成JavaWebToken
        String newToken = JavaWebTokenUtil.createToken(userId, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET, JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER, JavaWebTokenUtil.TC_BALL_TOKEN_EXPIRE_HOURS);
        Result<String> result = ResultBuild.wrapSuccess();
        result.setValue(newToken);
        return result;
    }

    private TcUser buildTcUser(SnsUser snsUser, SnsAccessToken snsAccessToken){
        TcUser tcUser = new TcUser();
        tcUser.setNickName(snsUser.getNickName());
        tcUser.setHeadImgUrl(snsUser.getHeadImgUrl());
        tcUser.setSex(snsUser.getSex());
        tcUser.setProvince(snsUser.getProvince());
        tcUser.setCity(snsUser.getCity());
        tcUser.setCounty(snsUser.getCountry());
        tcUser.setOpenId(snsUser.getOpenId());
        tcUser.setUnionId(snsUser.getUnionId());
        tcUser.setAccessToken(snsAccessToken.getAccessToken());
        tcUser.setRefreshToken(snsAccessToken.getRefreshToken());
        return tcUser;
    }

}
