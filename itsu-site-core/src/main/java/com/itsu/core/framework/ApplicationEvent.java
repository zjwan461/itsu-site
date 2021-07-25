package com.itsu.core.framework;

/**
 * @author Jerry.Su
 * @Date 2021/7/19 11:51
 */
public interface ApplicationEvent {

    void handle(String key, Object value);
}
