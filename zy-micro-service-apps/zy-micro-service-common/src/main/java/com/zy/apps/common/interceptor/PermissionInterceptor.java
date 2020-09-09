package com.zy.apps.common.interceptor;

import com.zy.apps.common.annotation.Permission;
import com.zy.apps.common.interfaces.PermissionHandler;
import com.zy.apps.common.kits.KeyValueKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZY
 * @since 2017/5/31
 */
@Configuration
@ConditionalOnBean(PermissionHandler.class)
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Value("${zy-micro-service-common.message.no-permission:No Permission!}")
    private String message;
    private PermissionHandler permissionHandler;
    private Map<String, String> cachePermission = new ConcurrentHashMap<>();

    public PermissionInterceptor(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // Only Intercept HandlerMethod
        if (!(o instanceof HandlerMethod)) {
            return true;
        }
        Permission permission = ((HandlerMethod) o).getMethodAnnotation(Permission.class);
        if (Objects.isNull(permission) || !permission.enable()) {
            return true;
        }
        // read permission from request if permission is empty
        String permissionStr = permission.value().isEmpty() ? getPermissionFromRequest(request) : permission.value();
        if (!permissionHandler.hasPermission(permissionStr)) {
            KeyValueKit.forbidden(message, permissionStr).write(response);
            return false;
        }
        return true;
    }

    private String getPermissionFromRequest(HttpServletRequest request) {
        String permission;
        String url = request.getRequestURI();
        if (!cachePermission.containsKey(url)) {
            permission = url.replace("/", ".");
            if (permission.startsWith(".")) {
                permission = permission.substring(1);
            }
            cachePermission.put(url, permission);
        }
        return cachePermission.get(url);
    }
}
