package com.siraon.rabbitmq.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author xielongwang
 * @create 2019-03-239:30 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Configuration
@EnableScheduling
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 5000)
    public void send() {
        rabbitTemplate.convertAndSend("custom.exchange","consumer","test");
    }
}