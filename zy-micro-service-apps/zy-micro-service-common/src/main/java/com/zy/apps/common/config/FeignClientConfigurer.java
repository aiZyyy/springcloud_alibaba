package com.zy.apps.common.config;

import com.zy.apps.common.interceptor.FeignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.RequestInterceptor;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2018/4/21 17:53.
 */
@Configuration
@ConditionalOnClass(name = "feign.RequestInterceptor")
public class FeignClientConfigurer {
    @Bean
    @ConditionalOnProperty(value = "zy-micro-service-common.feign.proxy.enabled", havingValue = "true")
    public RequestInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = "zy-micro-service-common.feign.debug", havingValue = "true")
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
