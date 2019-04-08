package com.siaron.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xielongwang
 * @create 2019-04-081:25 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Configuration
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaTopicConfiguration {

    private final KafkaTopicProperties properties;

    public KafkaTopicConfiguration(KafkaTopicProperties properties) {
        this.properties = properties;
    }


    @Bean
    public NewTopic topic1() {
        return new NewTopic(properties.getTopicName(), 10, (short) 0);
    }

    @Bean
    public String topicGroupId() {
        return properties.getGroupId();
    }

}