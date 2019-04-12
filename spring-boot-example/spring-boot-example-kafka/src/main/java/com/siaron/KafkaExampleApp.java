package com.siaron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xielongwang
 * @create 2019-01-219:49 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@EnableScheduling
@SpringBootApplication
public class KafkaExampleApp {

    public static void main(String[] args) {
        SpringApplication.run(KafkaExampleApp.class, args);
    }
}