package com.zy.apps.common.interfaces;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
public interface PermissionHandler {
    /**
     * 判断用户权限
     *
     * @param permission
     *         权限字符串
     * @return 返回用户是否有权限
     */
    boolean hasPermission(String permission);
}
