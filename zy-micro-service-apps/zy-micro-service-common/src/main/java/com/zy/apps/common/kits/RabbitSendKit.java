package com.zy.apps.common.kits;


import com.zy.apps.common.interfaces.ZyMessageCallback;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: ZY
 * @Date: 2019/8/2 15:40
 * @Version 1.0
 */
@Component
@ConditionalOnBean(ZyMessageCallback.class)
@ConditionalOnProperty(value = "spring.rabbitmq.host")
public class RabbitSendKit implements RabbitTemplate.ConfirmCallback {

    private ZyMessageCallback sixiMessageCallback;
    private RabbitTemplate rabbitTemplate;

    public RabbitSendKit(ZyMessageCallback sixiMessageCallback, RabbitTemplate rabbitTemplate) {
        this.sixiMessageCallback = sixiMessageCallback;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息
     *
     * @param routerKey 发送的队列
     * @param context   消息内容
     */
    public void send(String routerKey, Object context) {
        send(routerKey, UUID.randomUUID().toString(), context);
    }

    /**
     * 发送消息
     *
     * @param routerKey 发送的队列
     * @param id        消息ID 用于回调的时候使用
     * @param context   消息内容
     */
    public void send(String routerKey, String id, Object context) {
        rabbitTemplate.convertAndSend(routerKey, context, new CorrelationData(id));
    }

    /**
     * 发送消息
     *
     * @param exchange 交换器
     * @param context  消息内容
     */
    public void sendToExchange(String exchange, Object context) {
        rabbitTemplate.convertAndSend(exchange, "", context);
    }

    /**
     * 发送消息
     *
     * @param exchange 交换器
     * @param id       消息ID
     * @param context  消息内容
     */
    public void sendToExchange(String exchange, String id, Object context) {
        rabbitTemplate.convertAndSend(exchange, "", context, new CorrelationData(id));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        sixiMessageCallback.confirm(correlationData, ack, cause);
    }
}
