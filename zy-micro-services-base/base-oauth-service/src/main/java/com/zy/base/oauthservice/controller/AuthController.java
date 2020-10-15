package com.zy.base.oauthservice.controller;

import com.zy.apps.common.constant.AuthConstant;
import com.zy.base.oauthservice.entity.domain.Oauth2TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 * Created by fishCoder on 2020/7/17.
 */
@RestController
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/oauth/token")
    public Oauth2TokenDto postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .access_token(oAuth2AccessToken.getValue())
                .refresh_token(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();

        return oauth2TokenDto;
    }
}
