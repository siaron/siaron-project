package com.siaron.daemon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-02-2710:14 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Component
public class QueueListener {

    @RabbitHandler(isDefault = true)
    @RabbitListener(queues = "test", concurrency = "2-5")
    public void deviceChargeMonitor(Message message) {
        log.info("message {} ", message.getBody());
    }
}