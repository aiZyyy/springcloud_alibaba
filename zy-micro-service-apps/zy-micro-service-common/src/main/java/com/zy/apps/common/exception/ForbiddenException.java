package com.zy.apps.common.exception;

import lombok.Getter;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
public class ForbiddenException extends RuntimeException {
    @Getter
    private Integer errCode = 0;

    private ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Integer errCode) {
        super(message);
        this.errCode = errCode;
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(String message, Integer errCode, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
