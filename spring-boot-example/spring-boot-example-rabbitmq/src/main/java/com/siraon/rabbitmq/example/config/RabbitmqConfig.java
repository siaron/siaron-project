package com.siraon.rabbitmq.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xielongwang
 * @create 2019-03-3010:31 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Configuration
public class RabbitmqConfig {


    private static final String DEAD_LETTER_EXCHANGE = "dlx.exchange";
    private static final String DEAD_LETTER_KEY = "dlx-routing-key";

    @Bean
    public DirectExchange customExchange() {
        return new DirectExchange("custom.exchange", true, false);
    }

    @Bean
    public Queue customQueue() {
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_KEY);
        return new Queue("consumer", true, false, false, arguments);
    }

    @Bean
    public Binding customQueueBind(DirectExchange customExchange, Queue customQueue) {
        return BindingBuilder
                .bind(customQueue)
                .to(customExchange)
                .with(customQueue.getName());
    }

    //死信队列声明.

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_KEY);
    }

    @Bean
    public Binding deadLetterBinding(DirectExchange deadLetterExchange, Queue deadLetterQueue) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(deadLetterQueue.getName());
    }


    //TTL 队列

}