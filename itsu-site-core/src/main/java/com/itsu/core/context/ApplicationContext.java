package com.itsu.core.context;

import java.util.Collection;

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

    void publishSet(String key, Object value);

    void publishGet(String key, Object value);

    void publishRemove(String key, Object value);

    void publishClean(String key, Object value);

    void cleanUp();

    Collection<Object> list();

    Collection<String> keys();
}
