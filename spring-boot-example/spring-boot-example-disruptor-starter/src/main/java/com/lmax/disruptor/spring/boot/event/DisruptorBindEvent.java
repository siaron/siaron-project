package com.lmax.disruptor.spring.boot.event;

import java.util.StringJoiner;

/**
 * @author xielongwang
 * @create 2019-03-235:23 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class DisruptorBindEvent extends DisruptorEvent {

    /**
     * 当前事件绑定的数据对象
     */
    protected Object bind;

    public DisruptorBindEvent(Object source) {
        super(source);
    }

    public DisruptorBindEvent(Object source, Object bind) {
        super(source);
        this.bind = bind;
    }

    public Object getBind() {
        return bind;
    }

    public void bind(Object bind) {
        this.bind = bind;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DisruptorBindEvent.class.getSimpleName() + "[", "]")
                .add("bind=" + bind)
                .add("source=" + source)
                .toString();
    }
}
