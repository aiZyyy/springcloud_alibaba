package com.zy.common.config;

import com.zy.common.interfaces.ZyMessageCallback;
import com.zy.common.interfaces.ZyMessageListener;
import lombok.Data;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2018/5/18 10:05.
 */
@Configuration
@ConditionalOnProperty(value = "spring.rabbitmq.speed")
public class RabbitMessageQueueConfigurer {
    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory(RabbitConfig rabbitConfig) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConfig.getHost(), rabbitConfig.getPort());
        connectionFactory.setUsername(rabbitConfig.getUsername());
        connectionFactory.setPassword(rabbitConfig.getPassword());
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setChannelListeners(Collections.singletonList((channel, transactional) -> {
            try {
                channel.basicQos(0, 0, false);
            } catch (IOException ignored) {
            }
        }));
        connectionFactory.setExecutor(new ThreadPoolExecutor(
                rabbitConfig.getSpeed(),
                rabbitConfig.getSpeed(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                r -> new Thread(r, "RabbitHandlerThread")));
        connectionFactory.setChannelCacheSize(rabbitConfig.getSpeed());
        return connectionFactory;
    }

    @Bean
    @ConditionalOnBean(ZyMessageListener.class)
    @ConditionalOnMissingBean
    public SimpleMessageListenerContainer messageContainer(RabbitConfig rabbitConfig, ZyMessageListener sixiMessageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory(rabbitConfig));
        container.setQueueNames(rabbitConfig.getHandler());
        container.setMaxConcurrentConsumers(rabbitConfig.getSpeed());
        container.setConcurrentConsumers(rabbitConfig.getSpeed());
        //消息确认后才能删除
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(sixiMessageListener);
        return container;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZyMessageCallback sixiMessageCallback() {
        return new DefaultMessageCallback();
    }

    public static class DefaultMessageCallback implements ZyMessageCallback {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        }
    }

    /**
     * Created with IntelliJ IDEA
     *
     * @author ZY
     * Created on 2018/5/11 9:23.
     */
    @Data
    @Component
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public static class RabbitConfig {
        private String host;
        private Integer port;
        private String username;
        private String password;
        private Integer speed;
        private String[] handler;
    }
}
