package com.siaron.demo;

/**
 * @author xielongwang
 * @create 2019-01-2311:29 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class KafkaProperties {

    public static final String TOPIC = "topic1";
    public static final String KAFKA_SERVER_URL = "127.0.0.1";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {
    }

}