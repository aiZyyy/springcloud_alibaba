package com.zy.apps.common.interceptor;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2018/3/8 14:59.
 */
public class FeignInterceptor implements RequestInterceptor {
    @Value("${zy-micro-service.feign.proxy.header:Cookie,Authorization}")
    private String[] proxyHeader;

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Optional.ofNullable(attributes)
                .ifPresent(attr -> Arrays
                        .stream(proxyHeader)
                        .forEach(name -> Optional.ofNullable(attr.getRequest().getHeader(name))
                                .ifPresent(value -> template.header(name, value))));
    }
}
