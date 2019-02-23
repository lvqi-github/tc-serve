package com.tcxx.serve.web.resolver;

import com.tcxx.serve.core.annotation.AdminLoginUser;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.web.domain.AdminUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 作用是，判断Controller层中的参数，是否满足条件，满足条件则执行resolveArgument方法，不满足则跳过
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(AdminUser.class) && methodParameter.hasParameterAnnotation(AdminLoginUser.class);
    }

    /**
     * 在supportsParameter方法返回true的情况下才会被调用。用于处理一些业务，将返回值赋值给Controller层中的这个参数
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        String token = nativeWebRequest.getHeader("Authorization");
        //获取用户名
        String username = JavaWebTokenUtil.getAudience(token);
        return new AdminUser(username);
    }
}
