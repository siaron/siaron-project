package com.siaron.drools.producer;

import com.lmax.disruptor.spring.boot.DisruptorTemplate;
import com.lmax.disruptor.spring.boot.event.DisruptorBindEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.stream.LongStream;

/**
 * @author xielongwang
 * @create 2019-03-235:23 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "spring.disruptor.enabled")
public class Producer {

    @Autowired(required = false)
    protected DisruptorTemplate disruptorTemplate;

    @Scheduled(fixedDelay = 1000)
    public void send()  {
        LongStream.range(1, 20).forEach(s -> {
            DisruptorBindEvent event = new DisruptorBindEvent("message 111" + Math.random(), "message 2222" + Math.random());
            event.setEvent("Event-Output");
            event.setTag("TagA-Output");
            event.setKey("id-" + s + Math.random());
            disruptorTemplate.publishEvent(event);
        });
    }
}