package com.itsu.core.framework;

/**
 * @author Jerry.Su
 * @Date 2021/7/19 11:16
 */
public interface ApplicationContext {

    String getName();

    void init();

    void set(String key, Object value);

    Object get(String key);

    Object remove(String key);

    void clean();

    Object publishSet(String key, Object value);

    Object publishGet(String key, Object value);

    Object publishRemove(String key, Object value);

    Object publishClean(String key, Object value);

    void cleanUp();
}