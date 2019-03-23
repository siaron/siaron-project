package com.siaron.drools.consumer;

import com.lmax.disruptor.spring.boot.annotation.EventRule;
import com.lmax.disruptor.spring.boot.event.DisruptorBindEvent;
import com.lmax.disruptor.spring.boot.event.handler.DisruptorHandler;
import com.lmax.disruptor.spring.boot.event.handler.chain.HandlerChain;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-03-235:24 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@EventRule("/Event-Output/TagA-Output/**")
@Component("consumer")
public class Consumer implements DisruptorHandler<DisruptorBindEvent> {

    @Override
    public void doHandler(DisruptorBindEvent event, HandlerChain<DisruptorBindEvent> handlerChain) throws Exception {
        System.out.println(event);
    }
}
