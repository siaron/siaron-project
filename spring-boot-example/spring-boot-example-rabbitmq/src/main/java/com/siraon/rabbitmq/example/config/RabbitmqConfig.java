package com.siraon.rabbitmq.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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




    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(
            RabbitProperties properties,
            ObjectProvider<ConnectionNameStrategy> connectionNameStrategy)
            throws Exception {
        PropertyMapper map = PropertyMapper.get();
        CachingConnectionFactory factory = new CachingConnectionFactory(
                getRabbitConnectionFactoryBean(properties).getObject());

        //心跳新城
        factory.setConnectionThreadFactory(new ThreadFactory() {
            public final AtomicInteger id = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, MessageFormat.format("amqp-heart-{0}", id.getAndIncrement()));
            }
        });

        //默认的线程池和下面的差不多，在com.rabbitmq.client.impl.ConsumerWorkService构造方法中，大小为处理器的两倍
        //如果采用DirectRabbitListenerContainerFactory需要重写，增加线程池的中的线程数量,具体看你的应用是计算密集还是io密集，
        // io越密集，线程数设置越大
        //java.util.concurrent.Executors.DefaultThreadFactory
        final ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
            public final AtomicInteger id = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, MessageFormat.format("amqp-client-{0}", id.getAndIncrement()));
            }
        });

        //客户端线程池
        factory.setExecutor(executorService);

        map.from(properties::determineAddresses).to(factory::setAddresses);
        map.from(properties::isPublisherConfirms).to(factory::setPublisherConfirms);
        map.from(properties::isPublisherReturns).to(factory::setPublisherReturns);
        RabbitProperties.Cache.Channel channel = properties.getCache().getChannel();
        map.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
        map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
                .to(factory::setChannelCheckoutTimeout);
        RabbitProperties.Cache.Connection connection = properties.getCache()
                .getConnection();
        map.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
        map.from(connection::getSize).whenNonNull()
                .to(factory::setConnectionCacheSize);
        map.from(connectionNameStrategy::getIfUnique).whenNonNull()
                .to(factory::setConnectionNameStrategy);
        return factory;
    }

    private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(
            RabbitProperties properties) throws Exception {
        PropertyMapper map = PropertyMapper.get();
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        map.from(properties::determineHost).whenNonNull().to(factory::setHost);
        map.from(properties::determinePort).to(factory::setPort);
        map.from(properties::determineUsername).whenNonNull()
                .to(factory::setUsername);
        map.from(properties::determinePassword).whenNonNull()
                .to(factory::setPassword);
        map.from(properties::determineVirtualHost).whenNonNull()
                .to(factory::setVirtualHost);
        map.from(properties::getRequestedHeartbeat).whenNonNull()
                .asInt(Duration::getSeconds).to(factory::setRequestedHeartbeat);
        RabbitProperties.Ssl ssl = properties.getSsl();
        if (ssl.isEnabled()) {
            factory.setUseSSL(true);
            map.from(ssl::getAlgorithm).whenNonNull().to(factory::setSslAlgorithm);
            map.from(ssl::getKeyStoreType).to(factory::setKeyStoreType);
            map.from(ssl::getKeyStore).to(factory::setKeyStore);
            map.from(ssl::getKeyStorePassword).to(factory::setKeyStorePassphrase);
            map.from(ssl::getTrustStoreType).to(factory::setTrustStoreType);
            map.from(ssl::getTrustStore).to(factory::setTrustStore);
            map.from(ssl::getTrustStorePassword).to(factory::setTrustStorePassphrase);
            map.from(ssl::isValidateServerCertificate).to((validate) -> factory
                    .setSkipServerCertificateValidation(!validate));
            map.from(ssl::getVerifyHostname)
                    .to(factory::setEnableHostnameVerification);
        }
        map.from(properties::getConnectionTimeout).whenNonNull()
                .asInt(Duration::toMillis).to(factory::setConnectionTimeout);
        factory.afterPropertiesSet();
        return factory;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setTaskExecutor(new SimpleAsyncTaskExecutor("customer-consumer-"));
        return factory;
    }
}