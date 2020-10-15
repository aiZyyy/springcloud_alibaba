package com.zy.base.oauthservice.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Oauth2获取Token返回信息封装
 * Created by fishCoder on 2020/7/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2TokenDto {
    /**
     * 访问令牌
     */
    private String access_token;
    /**
     * 刷令牌
     */
    private String refresh_token;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;
}
