package com.zy.gateway.checksign.common.oauth.method;

import com.zy.gateway.checksign.common.oauth.AuthMessage;
import com.zy.gateway.checksign.common.oauth.domain.AuthConsumer;
import com.zy.gateway.checksign.common.oauth.exception.AuthException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: ZY
 * @Date: 2019/8/7 10:26
 * @Version 1.0
 * @Description: 校验请求授权是合法
 */
public interface AuthValidator {
    /**
     * 校验OAuthMessage 的授权是否合法
     * @param message 授权请求消息体
     * @param consumer 授权接入应用
     * @throws AuthException 非法授权
     */
    void validateMessage(AuthMessage message, AuthConsumer consumer, RedisTemplate<String, String> redisTemplate) throws AuthException;
}
