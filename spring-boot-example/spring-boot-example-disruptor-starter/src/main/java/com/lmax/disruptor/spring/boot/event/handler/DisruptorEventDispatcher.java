package com.lmax.disruptor.spring.boot.event.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.spring.boot.event.DisruptorEvent;
import com.lmax.disruptor.spring.boot.event.handler.chain.HandlerChain;
import com.lmax.disruptor.spring.boot.event.handler.chain.HandlerChainResolver;
import com.lmax.disruptor.spring.boot.event.handler.chain.ProxiedHandlerChain;
import org.springframework.core.Ordered;

/**
 * Disruptor 事件分发实现
 */
public class DisruptorEventDispatcher extends AbstractRouteableEventHandler<DisruptorEvent> implements EventHandler<DisruptorEvent>, WorkHandler<DisruptorEvent>, Ordered {

    private int order = 0;

    public DisruptorEventDispatcher(HandlerChainResolver<DisruptorEvent> filterChainResolver, int order) {
        super(filterChainResolver);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    /*
     * 责任链入口
     */
    public void onEvent(DisruptorEvent event, long sequence, boolean endOfBatch) throws Exception {
        //构造原始链对象
        HandlerChain<DisruptorEvent> originalChain = new ProxiedHandlerChain();
        //执行事件处理链
        this.doHandler(event, originalChain);

    }

    /*
     * 责任链入口 多个消费者
     */
    public void onEvent(DisruptorEvent event) throws Exception {
        //构造原始链对象
        HandlerChain<DisruptorEvent> originalChain = new ProxiedHandlerChain();
        //执行事件处理链
        this.doHandler(event, originalChain);
    }
}

