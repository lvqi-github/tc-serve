package com.tcxx.serve.web.interceptor;


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
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

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
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        //检查是否有PassTokenValidation注释，有则跳过验证
        if (method.isAnnotationPresent(PassTokenValidation.class)) {
            PassTokenValidation passToken = method.getAnnotation(PassTokenValidation.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 从http请求头中取出token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)){
            throw new RuntimeException("校验token失败, token为空");
        }

        //检查是否需要微信登陆权限
        if (method.isAnnotationPresent(WeChatLoginTokenValidation.class)) {
            WeChatLoginTokenValidation passToken = method.getAnnotation(WeChatLoginTokenValidation.class);
            if (passToken.required()) {
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
                if(res){ // 校验通过
                    // 校验微信授权access_token是否有效，无效则刷新
                    boolean accessTokenHas = redisUtil.hasKey(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId));
                    if (!accessTokenHas){ //缓存中没有说明已过期 进行刷新
                        Object refreshToken = redisUtil.get(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId));
                        if (Objects.isNull(refreshToken)){ //缓存中没有说明已29天没有登录过 要重新授权
                            throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR501);
                        }
                        SnsAccessToken snsAccessToken = weChatClient.sns().refreshToken(refreshToken.toString());
                        //TODO 这里要根据refreshToken是否改变决定逻辑 改变则accessToken、refreshToken缓存都更新 不改变则只更新accessToken（暂时先都更新 了解微信逻辑后处理）
                        //更新mysql
                        boolean b = tcUserService.updateAccessTokenAndRefreshToken(userId, snsAccessToken.getAccessToken(), snsAccessToken.getRefreshToken());
                        if (!b){
                            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
                        }
                        //更新redis
                        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_ACCESS_TOKEN, userId), snsAccessToken.getAccessToken(),  110 * 60); //110分钟
                        redisUtil.set(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId), snsAccessToken.getRefreshToken(),  29 * 24 * 60 * 60); //29天
                    }
                    return true;
                }else {// 校验失败
                    // token已失效，检查微信授权refresh_token是否失效，失效提示重新授权；未失效返回刷新token
                    boolean refreshTokenHas = redisUtil.hasKey(RedisKeyPrefix.getTcBallRedisKey(RedisKeyPrefix.TC_BALL_WECHAT_AUTH_REFRESH_TOKEN, userId));
                    if (!refreshTokenHas){
                        throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR501); //重新授权
                    }else {
                        throw new WeChatAuthorizeRuntimeException(ResultCodeEnum.ERROR501); //提示进行刷新token
                    }
                }
            }
        }

        return false;
    }
}
