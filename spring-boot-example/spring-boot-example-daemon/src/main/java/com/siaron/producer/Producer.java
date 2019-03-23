package com.siaron.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;


/**
 * @author xielongwang
 * @create 2019-03-239:30 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Configuration
@EnableScheduling
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 5000)
    public void send() {
        rabbitTemplate.convertAndSend("test", UUID.randomUUID().toString());
    }
}