package com.tcxx.serve.web.interceptor;


import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.exception.JavaWebTokenVerifyRuntimeException;
import com.tcxx.serve.core.exception.WeChatAuthorizeRuntimeException;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import com.tcxx.serve.wechat.model.sns.SnsUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private TcUserService tcUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查是否有PassTokenValidation注释，有则跳过验证
        if (method.isAnnotationPresent(PassTokenValidation.class)) {
            PassTokenValidation passTokenValidation = method.getAnnotation(PassTokenValidation.class);
            if (passTokenValidation.required()) {
                return true;
            }
        }

        // 从http请求头中取出token
        String token = request.getHeader("Authorization");
        log.info("jwt token=" + token);
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("校验token失败, token为空");
        }

        //检查是否需要微信登陆权限
        if (method.isAnnotationPresent(WeChatLoginTokenValidation.class)) {
            WeChatLoginTokenValidation weChatLoginTokenValidation = method.getAnnotation(WeChatLoginTokenValidation.class);
            if (weChatLoginTokenValidation.required()) {
                //获取签发者
                String issuer = JavaWebTokenUtil.getIssuer(token);
                //判断签发者是否合法
                if (!JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER.equals(issuer)) {
                    throw new JavaWebTokenVerifyRuntimeException(ResultCodeEnum.ERROR4501);
                }
                //获取用户id
                String userId = JavaWebTokenUtil.getAudience(token);
                //校验token
                boolean res = JavaWebTokenUtil.verifyToken(token, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET);
                if (res) { // 校验通过
                    // 校验微信授权access_token是否有效，无效则刷新
                    boolean accessTokenHas = redisUtil.hasKey(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId));
                    if (!accessTokenHas) { //缓存中没有说明已过期 进行刷新
                        Object refreshToken = redisUtil.get(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId));
                        if (Objects.isNull(refreshToken)) { //缓存中没有说明已29天没有登录过 要重新授权
                            throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR1501);
                        }
                        SnsAccessToken snsAccessToken = weChatClient.sns().refreshToken(refreshToken.toString());
                        //更新redis accessToken
                        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId), snsAccessToken.getAccessToken(), 120 * 60); //120分钟
                        // 获取微信用户信息
                        SnsUser snsUser = weChatClient.sns().getSnsUser(snsAccessToken.getAccessToken(), snsAccessToken.getOpenId());
                        //判断与mysql中的用户信息是否一致 不一致则更新
                        TcUser tcUser = tcUserService.getByUserId(userId);
                        if (!verifyUserInfoIsConsistent(tcUser, snsUser)){
                            TcUser updateUser = buildTcUser(snsUser);
                            updateUser.setId(userId);
                            boolean update = tcUserService.update(updateUser);
                            if (!update){
                                throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
                            }
                        }
                    }
                    return true;
                } else {// 校验失败
                    // token已失效，因生成的token有效期为31天 大于 微信授权refresh_token的有效期，所以直接返回重新授权
                    throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR1501); //重新授权
                }
            }
        }

        //检查是否需要后台登陆权限
        if (method.isAnnotationPresent(AdminLoginTokenValidation.class)) {
            AdminLoginTokenValidation adminLoginTokenValidation = method.getAnnotation(AdminLoginTokenValidation.class);
            if (adminLoginTokenValidation.required()) {
                //获取签发者
                String issuer = JavaWebTokenUtil.getIssuer(token);
                //判断签发者是否合法
                if (!JavaWebTokenUtil.TC_ADMIN_TOKEN_ISSUER.equals(issuer)) {
                    throw new JavaWebTokenVerifyRuntimeException(ResultCodeEnum.ERROR4501);
                }
                //获取用户名
                String username = JavaWebTokenUtil.getAudience(token);
                //校验token
                boolean res = JavaWebTokenUtil.verifyToken(token, JavaWebTokenUtil.TC_ADMIN_TOKEN_SECRET);
                if (res) { // 校验通过
                    return true;
                } else {// 校验失败
                    // token已失效，返回重新登陆
                    throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR4502);
                }
            }
        }

        return false;
    }

    private boolean verifyUserInfoIsConsistent(TcUser tcUser, SnsUser snsUser) {
        String userStr = String.format("%s_%s_%s_%s_%s_%s", tcUser.getNickName(), tcUser.getHeadImgUrl(), tcUser.getSex(), tcUser.getProvince(), tcUser.getCity(), tcUser.getCountry());
        String snsUserStr = String.format("%s_%s_%s_%s_%s_%s", snsUser.getNickName(), snsUser.getHeadImgUrl(), snsUser.getSex(), snsUser.getProvince(), snsUser.getCity(), snsUser.getCountry());
        return userStr.equals(snsUserStr);
    }

    private TcUser buildTcUser(SnsUser snsUser){
        TcUser tcUser = new TcUser();
        tcUser.setNickName(snsUser.getNickName());
        tcUser.setHeadImgUrl(snsUser.getHeadImgUrl());
        tcUser.setSex(snsUser.getSex());
        tcUser.setProvince(snsUser.getProvince());
        tcUser.setCity(snsUser.getCity());
        tcUser.setCountry(snsUser.getCountry());
        return tcUser;
    }

}
