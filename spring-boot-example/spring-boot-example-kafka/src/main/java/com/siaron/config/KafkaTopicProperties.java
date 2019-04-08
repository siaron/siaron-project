package com.siaron.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author xielongwang
 * @create 2019-04-081:24 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@ConfigurationProperties("kafka.topic")
@Data
public class KafkaTopicProperties implements Serializable {

    private String groupId;
    private String  topicName;
}