package com.itsu.core.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jerry.Su
 * @Date 2021/7/19 11:21
 */
public class DefaultApplicationContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationContext.class);

    private static final ConcurrentHashMap<String, Object> applicationCache = new ConcurrentHashMap<>();

    private static DefaultApplicationContext dac;

    private DefaultApplicationContext() {
        init();
    }

    @Override
    public String getName() {
        return "itsu-site";
    }

    @Override
    public void init() {
    }

    @Override
    public synchronized void set(String key, Object value) {
        Object v = get(key);
        if (v != null && (v == value || v.equals(value))) {
            logger.debug("current value is already in applicationContext, will not to set again");
        } else {
            applicationCache.put(key, value);
            publishSet(key, value);
        }

    }

    @Override
    public synchronized Object get(String key) {
        if (key == null || key.length() == 0) {
            logger.debug("current key is null or empty, will not set to applicationContext");
            return null;
        } else {
            Object value = applicationCache.get(key);
            publishGet(key, value);
            return value;
        }
    }

    @Override
    public synchronized Object remove(String key) {
        if (key == null || key.length() == 0) {
            logger.debug("current key is null or empty, will not set to applicationContext");
            return null;
        } else {
            Object value = applicationCache.remove(key);
            publishRemove(key, value);
            return value;
        }
    }

    @Override
    public synchronized void clean() {
        publishClean(null, null);
        applicationCache.clear();
    }

    @Override
    public Object publishSet(String key, Object value) {
        return DefaultApplicationEvent.SET.publish(key, value);
    }

    @Override
    public Object publishGet(String key, Object value) {
        return DefaultApplicationEvent.GET.publish(key, value);
    }

    @Override
    public Object publishRemove(String key, Object value) {
        return DefaultApplicationEvent.REMOVE.publish(key, value);
    }

    @Override
    public Object publishClean(String key, Object value) {
        return DefaultApplicationEvent.CLEAN.publish(null, null);
    }

    @PreDestroy
    @Override
    public void cleanUp() {
        this.clean();
    }

    public static ApplicationContext getInstance() {
        if (dac == null) {
            dac = new DefaultApplicationContext();
        }
        return dac;
    }
}
