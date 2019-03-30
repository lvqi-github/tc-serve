package com.tcxx.serve.web.configuration;

import com.tcxx.serve.web.interceptor.AuthenticationInterceptor;
import com.tcxx.serve.web.interceptor.RequestSignValidateInterceptor;
import com.tcxx.serve.web.resolver.AdminUserMethodArgumentResolver;
import com.tcxx.serve.web.resolver.WeChatUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestSignValidateInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminUserMethodArgumentResolver());
        resolvers.add(weChatUserMethodArgumentResolver());
    }

    @Bean
    public RequestSignValidateInterceptor requestSignValidateInterceptor() {
        return new RequestSignValidateInterceptor();
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public AdminUserMethodArgumentResolver adminUserMethodArgumentResolver() {
        return new AdminUserMethodArgumentResolver();
    }

    @Bean
    public WeChatUserMethodArgumentResolver weChatUserMethodArgumentResolver() {
        return new WeChatUserMethodArgumentResolver();
    }

}
