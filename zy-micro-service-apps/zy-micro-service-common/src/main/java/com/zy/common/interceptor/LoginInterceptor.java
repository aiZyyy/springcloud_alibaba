package com.zy.common.interceptor;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sixi.micro.common.annotation.BypassLogin;
import com.sixi.micro.common.interfaces.LoginHandler;
import com.sixi.micro.common.utils.KeyValue;

/**
 * Created with IntelliJ IDEA
 *
 * @author MiaoWoo
 * Created on 2018/12/4 16:29.
 */
@Configuration
@ConditionalOnBean(LoginHandler.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Value("${sixi-micro-service-common.message.no-login:User Not Login!}")
    private String message;
    private LoginHandler loginHandler;

    public LoginInterceptor(LoginHandler loginHandler) {this.loginHandler = loginHandler;}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // Only Intercept HandlerMethod
        if (!(o instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) o;
        BypassLogin bypassLogin = Optional.ofNullable(method.getMethodAnnotation(BypassLogin.class))
                .orElseGet(() -> method.getBeanType().getAnnotation(BypassLogin.class));
        if (Objects.nonNull(bypassLogin) || loginHandler.isLogin()) {
            return true;
        }
        KeyValue.rd(HttpStatus.UNAUTHORIZED.value(), message).write(response);
        return false;
    }
}
