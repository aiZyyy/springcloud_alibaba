package com.zy.apps.common.interfaces;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
public interface UserHandler extends LoginHandler {
    /**
     * 获得用户ID
     *
     * @return 用户ID
     */
    String getId();
}
