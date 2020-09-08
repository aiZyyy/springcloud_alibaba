package com.zy.gateway.checksign.common.oauth.method.impl;

import com.zy.gateway.checksign.common.oauth.Auth;
import com.zy.gateway.checksign.common.oauth.domain.AuthConsumer;
import com.zy.gateway.checksign.common.oauth.exception.AuthException;
import com.zy.gateway.checksign.common.oauth.exception.AuthProblemException;
import com.zy.gateway.checksign.common.oauth.method.AuthProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZY
 * @Date: 2019/8/7 10:25
 * @Version 1.0
 * @Description: 简单OAuthProvider实现, APP ID与APP Secret 由Spring配置
 * 校验请求授权是合法
 */
public class SimpleAuthProvider implements AuthProvider {


    private Map<String, AuthConsumer> consumeMap;

    public SimpleAuthProvider(Map<String, String> cache) {
        consumeMap = new ConcurrentHashMap<>();
        if (cache != null) {
            for (Map.Entry<String, String> entry : cache.entrySet()) {
                AuthConsumer consumer = new AuthConsumer(entry.getKey(), entry.getValue());
                consumeMap.put(entry.getKey(), consumer);
            }
        }
    }

    @Override
    public AuthConsumer getAccessConsumer(String appId) throws AuthException {
        AuthConsumer consumer = consumeMap.get(appId);
        if (consumer == null) {
            AuthProblemException problem = new AuthProblemException(Auth.Problems.CONSUMER_KEY_UNKNOWN);
            throw problem;
        }
        return consumer;
    }

}
