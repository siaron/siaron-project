package com.siaron.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-04-081:26 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Component
public class KafkaConsumer {

    private Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    //关于consumer的主要的封装在ConcurrentKafkaListenerContainerFactory这个里头，本身的 KafkaConsumer 是线程不安全的，
    //无法并发操作，这里spring又在包装了一层，根据配置的spring.kafka.listener.concurrency来生成多个并发的 KafkaMessageListenerContainer 实例.
    //ContainerProperties.setConsumerTaskExecutor  每个 KafkaConsumer 对应一个 SimpleAsyncTaskExecutor .
    @KafkaListener(topics = "topic1", groupId = "#{topicGroupId}", concurrency = "15")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        LOG.info("processMessage, topic = {} current Thread {}", record, Thread.currentThread().getName());
    }

}