package com.junseong.demospringsecurityweb.config;

import com.junseong.demospringsecurityweb.account.AccountListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AccountConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccountListener())
                .addPathPatterns("/");

    }
}
