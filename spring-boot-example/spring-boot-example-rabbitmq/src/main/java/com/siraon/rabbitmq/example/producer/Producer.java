package com.siraon.rabbitmq.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;


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

//    @Scheduled(fixedDelay = 5000)
//    public void send() {
//        rabbitTemplate.convertAndSend("custom.exchange", "consumer", "test");
//    }
//
//
//    @Scheduled(fixedDelay = 5000)
//    public void sendTtl() {
//        //指定消息的生存时间
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration("5000");
//        Message message = new Message("test".getBytes(), messageProperties);
//        rabbitTemplate.send("ttl.exchange", "delay_queue_per_message_ttl", message);
//
//        //指定队列中全部的消息TTL
//        rabbitTemplate.convertAndSend("ttl.exchange", "ttlQueue", "test");
//    }
}