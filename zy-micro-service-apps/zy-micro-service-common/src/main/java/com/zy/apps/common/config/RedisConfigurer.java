package com.zy.apps.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2018/5/11 12:42.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "spring.redis.host")
public class RedisConfigurer {
    @Bean(value = "redisTemplate")
    @ConditionalOnClass(name = "com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer")
    @ConditionalOnProperty(value = "zy-micro-service-common.redis.fast-json-serializer", havingValue = "true", matchIfMissing = true)
    public <V> RedisTemplate<String, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("初始化 RedisTemplate 设置 FastJsonRedisSerializer 默认序列化...");
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        return redisTemplate;
    }
}
