package com.tcxx.serve.web.resolver;

import com.tcxx.serve.core.annotation.WeChatLoginUser;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.web.domain.ball.WeChatUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class WeChatUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(WeChatUser.class) && methodParameter.hasParameterAnnotation(WeChatLoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader("Authorization");
        //获取用户id
        String userId = JavaWebTokenUtil.getAudience(token);
        return new WeChatUser(userId);
    }
}
