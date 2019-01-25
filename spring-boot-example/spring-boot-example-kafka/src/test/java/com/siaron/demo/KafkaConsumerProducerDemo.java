package com.siaron.demo;

/**
 * @author xielongwang
 * @create 2019-01-2311:26 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class KafkaConsumerProducerDemo {


    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        SiaronProduct producerThread = new SiaronProduct(KafkaProperties.TOPIC, isAsync);
        producerThread.start();

        SiaronConsumer consumerThread = new SiaronConsumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}