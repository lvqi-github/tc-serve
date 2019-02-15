package com.tcxx.serve.web.interceptor;


import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.utils.JavaWebTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

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
                //获取用户id
                String userId = JavaWebTokenUtil.getAudience(token);

                boolean res = JavaWebTokenUtil.verifyToken(userId, token, JavaWebTokenUtil.WECHAT_TOKEN_SECRET, issuer);
                if(res){ // 校验通过
                    // TODO 校验微信授权access_token是否有效，无效则刷新
                    return true;
                }else {// 校验失败
                    // TODO 校验issuer与userId是否合法，不合法返回；合法时说明token已失效，检查微信授权refresh_token是否失效，失效提示重新授权；未失效返回刷新token
                    return false;
                }
            }
        }

        return false;
    }
}
