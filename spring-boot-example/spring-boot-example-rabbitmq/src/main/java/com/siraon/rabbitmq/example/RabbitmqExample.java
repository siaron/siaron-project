package com.siraon.rabbitmq.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ErrorHandler;

import java.util.concurrent.CountDownLatch;

/**
 * @author xielongwang
 * @create 2019-03-3010:15 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@SpringBootApplication
public class RabbitmqExample {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication app = new SpringApplication(RabbitmqExample.class);
        ConfigurableApplicationContext context = app.run(args);
        context.registerShutdownHook();
        final CountDownLatch closeLatch = context.getBean(CountDownLatch.class);
        Runtime.getRuntime().addShutdownHook(new Thread(closeLatch::countDown));
        closeLatch.await();
    }

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }


}