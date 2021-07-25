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

    private ConcurrentHashMap<String, Object> applicationCache = null;

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
        applicationCache = new ConcurrentHashMap<>();
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
        applicationCache.clear();
        publishClean(null, null);
    }

    @Override
    public void publishSet(String key, Object value) {
        DefaultApplicationEvent.SET.handle(key, value);
    }

    @Override
    public void publishGet(String key, Object value) {
        DefaultApplicationEvent.GET.handle(key, value);
    }

    @Override
    public void publishRemove(String key, Object value) {
        DefaultApplicationEvent.REMOVE.handle(key, value);
    }

    @Override
    public void publishClean(String key, Object value) {
        DefaultApplicationEvent.CLEAN.handle(null, null);
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
