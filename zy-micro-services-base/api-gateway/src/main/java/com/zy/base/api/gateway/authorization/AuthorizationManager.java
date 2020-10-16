package com.zy.base.api.gateway.authorization;

import cn.hutool.core.convert.Convert;
import com.zy.apps.common.constant.AuthConstant;
import com.zy.apps.common.constant.GatewayConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by fishCoder on 2020/6/19.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        Set<Object> ignoreUrls = redisTemplate.opsForHash().keys(GatewayConstant.SKIP_ROUTES);
        for (Object ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl.toString(), uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
//        //不同用户体系登录不允许互相访问
//        try {
//            String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
//            if (StrUtil.isEmpty(token)) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
//            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String userStr = jwsObject.getPayload().toString();
//            UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
//            if (AuthConstant.DEMO_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
//            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return Mono.just(new AuthorizationDecision(false));
//        }
//        //非管理端路径直接放行
//        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//            return Mono.just(new AuthorizationDecision(true));
//        }
        //管理端路径需校验权限
        Object obj = redisTemplate.opsForHash().get(AuthConstant.RESOURCE_ROLES_MAP_KEY, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
