package com.siaron.drools.consumer;

import com.lmax.disruptor.spring.boot.annotation.EventRule;
import com.lmax.disruptor.spring.boot.event.DisruptorBindEvent;
import com.lmax.disruptor.spring.boot.event.handler.DisruptorHandler;
import com.lmax.disruptor.spring.boot.event.handler.chain.HandlerChain;
import com.siaron.drools.model.data.SensorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-03-235:24 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Component
@EventRule("/Event-Output/TagA-Output/**")
public class Consumer implements DisruptorHandler<DisruptorBindEvent> {

    @Override
    public void doHandler(DisruptorBindEvent event, HandlerChain<DisruptorBindEvent> handlerChain) {
        if (event.getBind() != null && event.getBind() instanceof DisruptorBindEvent) {
            DisruptorBindEvent disruptorBindEvent = (DisruptorBindEvent) event.getBind();
            if (disruptorBindEvent.getBind() != null && disruptorBindEvent.getBind() instanceof SensorDTO) {
                SensorDTO sensor = (SensorDTO) disruptorBindEvent.getBind();


                log.info("receiver sensor data :{}", sensor);
            }
        }
    }
}
