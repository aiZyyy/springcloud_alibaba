package com.zy.apps.common.interceptor;

import com.zy.apps.common.annotation.RequestLimit;
import com.zy.apps.common.interfaces.LoginHandler;
import com.zy.apps.common.interfaces.UserHandler;
import com.zy.apps.common.kits.KeyValueKit;
import com.zy.apps.common.kits.WebKit;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author ZY
 */
@Configuration
@ConditionalOnBean({LoginHandler.class, UserHandler.class})
@ConditionalOnProperty(name = "zy-micro-service-common.http.limit.enabled", havingValue = "true")
public class LimitInterceptor extends HandlerInterceptorAdapter {
    private static final String MARK = "LIMIT";
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private LoginHandler loginHandler;
    private UserHandler userHandler;

    public LimitInterceptor(LoginHandler loginHandler, UserHandler userHandler) {
        this.loginHandler = loginHandler;
        this.userHandler = userHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        val limit = ((HandlerMethod) handler).getMethodAnnotation(RequestLimit.class);
        if (limit == null || !limit.enable()) {
            return true;
        }
        String key = "LimitInterceptor:" + request.getRequestURI() + ":" + (loginHandler.isLogin() ? userHandler.getId() : WebKit.getClientIP());
        String value = redisTemplate.opsForValue().get(key);
        if (!org.springframework.util.StringUtils.isEmpty(value)) {
            KeyValueKit.forbidden("请勿重复提交!").write(response);
            return false;
        }
        redisTemplate.opsForValue().set(key, MARK, limit.value(), TimeUnit.SECONDS);
        return true;
    }
}
