package com.siraon.rabbitmq.example.config;

import org.springframework.amqp.core.*;
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


    //TTL + DLX 实现延迟队列


    @Bean
    public DirectExchange ttlExchange() {
        return new DirectExchange("ttl.exchange", true, false);
    }

    @Bean
    public Queue ttlQueue() {
        //该队列中的所有消息的过期时间为5秒,然后发到死信队列中
        Map<String, Object> args = new HashMap<String, Object>();
        //指定此队列的生命周期为5秒
        //args.put("x-expires", 5000);
        //指定此队列的所有消息生命周期为6秒
        args.put("x-message-ttl", 6000);
        args.put("x-dead-letter-exchange", "ttl.to.dlx.exchange");
        args.put("x-dead-letter-routing-key", "ttl2dlxKey");
        return new Queue("ttlQueue", true, false, false, args);
    }

    @Bean
    public Binding ttlBinding(DirectExchange ttlExchange, Queue ttlQueue) {
        return BindingBuilder.bind(ttlQueue).to(ttlExchange).with(ttlQueue.getName());
    }

    /**
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 每个message可以控制自己的失效时间
     */
    final static String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl";

    /**
     * 创建delay_queue_per_message_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                // DLX，dead letter发送到的exchange
                .withArgument("x-dead-letter-exchange", "ttl.to.dlx.exchange")
                // dead letter携带的routing key
                .withArgument("x-dead-letter-routing-key", "ttl2dlxKey")
                .build();
    }

    /**
     * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列
     *
     */
    @Bean
    Binding queueTTLBinding(Queue delayQueuePerMessageTTL, DirectExchange ttlExchange) {
        return BindingBuilder.bind(delayQueuePerMessageTTL)
                .to(ttlExchange)
                .with(delayQueuePerMessageTTL.getName());
    }

    //ttl 死信队列

    @Bean
    public DirectExchange ttlDeadLetterExchange() {
        return new DirectExchange("ttl.to.dlx.exchange", true, false);
    }

    @Bean
    public Queue ttlDeadLetterQueue() {
        return new Queue("ttl2dlxQueue");
    }

    @Bean
    public Binding ttlDeadLetterBinding(DirectExchange ttlDeadLetterExchange, Queue ttlDeadLetterQueue) {
        return BindingBuilder.bind(ttlDeadLetterQueue).to(ttlDeadLetterExchange).with("ttl2dlxKey");
    }
}