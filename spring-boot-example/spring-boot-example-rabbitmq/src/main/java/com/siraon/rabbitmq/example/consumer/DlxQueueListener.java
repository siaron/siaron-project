package com.siraon.rabbitmq.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author xielongwang
 * @create 2019-02-2710:14 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Component
public class DlxQueueListener {

    @RabbitListener(queues = "#{customQueue}", concurrency = "1-5", returnExceptions = "false")
    public void deviceChargeMonitor(String message) throws AmqpRejectAndDontRequeueException {
        try {
            //int a = 1 / 0;
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("test");
        }
    }
}