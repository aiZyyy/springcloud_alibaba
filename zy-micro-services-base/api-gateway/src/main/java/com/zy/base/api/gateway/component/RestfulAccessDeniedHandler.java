package com.zy.base.api.gateway.component;

import cn.hutool.json.JSONUtil;
import com.zy.apps.common.domain.vo.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

import static com.zy.base.api.gateway.component.RestAuthenticationEntryPoint.getServerHttpResponse;


/**
 * 自定义返回结果：没有权限访问时
 * Created by fishCoder on 2018/4/26.
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = getServerHttpResponse(exchange);
        String body= JSONUtil.toJsonStr(Result.forbidden(denied.getMessage()));
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
