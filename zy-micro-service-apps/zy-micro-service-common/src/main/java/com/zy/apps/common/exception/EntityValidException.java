package com.zy.apps.common.exception;


import com.zy.apps.common.kits.KeyValueKit;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
public class EntityValidException extends RuntimeException {
    private KeyValueKit keyValue;

    public EntityValidException() {
    }

    public EntityValidException(KeyValueKit keyValue) {
        this.keyValue = keyValue;
    }

    public EntityValidException(String message) {
        super(message);
    }

    public EntityValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityValidException(Throwable cause) {
        super(cause);
    }

    public EntityValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public KeyValueKit getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(KeyValueKit keyValue) {
        this.keyValue = keyValue;
    }
}
