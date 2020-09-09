package com.zy.apps.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zy.apps.common.annotation.Exclude;
import com.zy.apps.common.annotation.Include;
import com.zy.apps.common.convert.converter.CustomFastJsonMessageConverter;
import com.zy.apps.common.kits.BeanKit;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author ZY
 */
@Configuration
@ConditionalOnProperty(value = "zy-micro-service-common.web-mvc-configurer.enabled", havingValue = "true", matchIfMissing = true)
public class MvcConfigurer extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Value("${zy-micro-service-common.session.create.policy:STATELESS}")
    private SessionCreationPolicy sessionCreationPolicy = SessionCreationPolicy.STATELESS;

    /**
     * 关闭 Basic 效验
     *
     * @param http HttpSecurity
     * @throws Exception Ex
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(sessionCreationPolicy)
                .and()
                .csrf().disable()
                .httpBasic().disable();
        // @formatter:on
    }

    /**
     * 自动扫描相关的拦截器并且注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        BeanKit.getListOfType(HandlerInterceptor.class).forEach(handlerInterceptor -> {
            val interceptor = registry.addInterceptor(handlerInterceptor);
            val exclude = handlerInterceptor.getClass().getAnnotation(Exclude.class);
            if (Objects.nonNull(exclude)) {
                Arrays.stream(exclude.value()).forEach(interceptor::excludePathPatterns);
            }
            val include = handlerInterceptor.getClass().getAnnotation(Include.class);
            if (Objects.nonNull(include)) {
                Arrays.stream(include.value()).forEach(interceptor::addPathPatterns);
            }
        });
    }

    /**
     * 自动扫描相关的参数解析器并且注册
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(BeanKit.getListOfType(HandlerMethodArgumentResolver.class));
    }

    /**
     * 自动扫描相关组件并且注册
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (Converter<?, ?> converter : BeanKit.getListOfType(Converter.class)) {
            registry.addConverter(converter);
        }
        for (GenericConverter converter : BeanKit.getListOfType(GenericConverter.class)) {
            registry.addConverter(converter);
        }
        for (Formatter<?> formatter : BeanKit.getListOfType(Formatter.class)) {
            registry.addFormatter(formatter);
        }
    }

    /**
     * 消息类型转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof AbstractJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new CustomFastJsonMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
    }
}
