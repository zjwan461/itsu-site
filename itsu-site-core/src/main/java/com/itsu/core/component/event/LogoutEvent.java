package com.itsu.core.component.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Jerry Su
 * @Date 2021/7/24 18:45
 */
public class LogoutEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LogoutEvent(Object source) {
        super(source);
    }
}
