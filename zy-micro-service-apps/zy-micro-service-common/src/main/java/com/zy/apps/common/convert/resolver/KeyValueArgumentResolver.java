package com.zy.apps.common.convert.resolver;

import com.zy.apps.common.kits.KeyValueKit;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2017/6/12 19:18.
 */
@Component
public class KeyValueArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return KeyValueKit.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        KeyValueKit kv = new KeyValueKit();
        webRequest.getParameterMap().forEach((k, v) -> {
            if (v.length == 1) {
                kv.put(k, v[0]);
            } else {
                kv.put(k, Arrays.asList(v));
            }
        });
        return kv;
    }
}
