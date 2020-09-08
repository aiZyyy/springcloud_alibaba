package com.zy.gateway.checksign.common.oauth.method;

import com.zy.gateway.checksign.common.oauth.exception.AuthProblemException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: ZY
 * @Date: 2019/8/7 10:18
 * @Version 1.0
 * @Description: auth 随机变量,校验nonce是否重复
 */
public interface AuthNonces {

    /**
     * 校验nonce 是否存在重复
     *
     * @param timestamp 时间戳
     * @param appId     appId
     * @param nonce     随机变量
     * @param redisTemplate redis模板
     * @throws AuthProblemException nonce 失败原因
     */
    void validateNonce(long timestamp, String appId, String nonce, RedisTemplate<String, String> redisTemplate) throws AuthProblemException;
}
