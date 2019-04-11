package com.siaron.producer;

import com.siaron.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.stream.LongStream;

/**
 * @author xielongwang
 * @create 2019-04-081:26 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@EnableScheduling
@Component
public class KafkaProducer {

    private Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);


    private final KafkaTemplate<Integer, String> kafkaTemplate;

    /**
     * 注入KafkaTemplate
     *
     * @param kafkaTemplate kafka模版类
     */
    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void producerMsg() {
        sendMessage("topic1", UUID.randomUUID().toString());
    }


    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data );
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
            }
        });
    }

}