package com.zy.apps.common.interfaces;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
public interface ZyMessageCallback {
    /**
     * Confirmation callback.
     *
     * @param correlationData
     *         correlation data for the callback.
     * @param ack
     *         true for ack, false for nack
     * @param cause
     *         An optional cause, for nack, when available, otherwise null.
     */
    void confirm(CorrelationData correlationData, boolean ack, String cause);
}
