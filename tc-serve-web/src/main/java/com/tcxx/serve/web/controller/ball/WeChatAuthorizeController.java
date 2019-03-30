package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginUser;
import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.web.domain.ball.WeChatUser;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import com.tcxx.serve.wechat.model.sns.SnsUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        // 判断用户是否存在 存在则更新 不存在创建 TODO 目前使用openId 后期改为unionId 防止多公众号或小程序openId不一致问题
        TcUser user = tcUserService.getByOpenId(snsUser.getOpenId());
        String userId;
        if (Objects.isNull(user)){
            // 创建用户 保存数据库
            userId = tcUserService.insert(buildTcUser(snsUser));
        }else {
            TcUser updateUser = buildTcUser(snsUser);
            updateUser.setId(user.getId());
            boolean update = tcUserService.update(updateUser);
            if (!update){
                throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
            }
            userId = user.getId();
        }
        // accessToken、refreshToken缓存redis
        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId), snsAccessToken.getAccessToken(),  120 * 60); //120分钟
        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId), snsAccessToken.getRefreshToken(),  30 * 24 * 60 * 60); //30天
        // 生成JavaWebToken
        String token = JavaWebTokenUtil.createToken(userId, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET, JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER, JavaWebTokenUtil.TC_BALL_TOKEN_EXPIRE_HOURS);

        Result<String> result = ResultBuild.wrapSuccess();
        result.setValue(token);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getUserInfo")
    public Result<Map<String, Object>> getAdminUserInfo(@WeChatLoginUser WeChatUser weChatUser) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }

        TcUser tcUser = tcUserService.getByUserId(weChatUser.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("username", tcUser.getNickName());
        map.put("avatar", tcUser.getHeadImgUrl());

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValue(map);
        return result;
    }

    private TcUser buildTcUser(SnsUser snsUser){
        TcUser tcUser = new TcUser();
        tcUser.setNickName(snsUser.getNickName());
        tcUser.setHeadImgUrl(snsUser.getHeadImgUrl());
        tcUser.setSex(snsUser.getSex());
        tcUser.setProvince(snsUser.getProvince());
        tcUser.setCity(snsUser.getCity());
        tcUser.setCountry(snsUser.getCountry());
        tcUser.setOpenId(snsUser.getOpenId());
        tcUser.setUnionId(snsUser.getUnionId());
        return tcUser;
    }

}
