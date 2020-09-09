package com.zy.apps.common.convert.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 自定义FastJson消息转换器
 *
 * @author ZY
 */
public class CustomFastJsonMessageConverter extends FastJsonHttpMessageConverter {
    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (contentType.isWildcardType() || contentType.isWildcardSubtype() || contentType == MediaType.APPLICATION_OCTET_STREAM) {
            contentType = MediaType.APPLICATION_JSON_UTF8;
        }
        super.write(o, type, contentType, outputMessage);
    }
}
