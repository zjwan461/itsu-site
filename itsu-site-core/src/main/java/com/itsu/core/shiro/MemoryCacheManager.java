package com.itsu.core.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Jerry.Su
 * @Date 2021/7/14 17:21
 */
public class MemoryCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(MemoryCacheManager.class);

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    private Long expire = DEFAULT_EXPIRE;

    public static final Long DEFAULT_EXPIRE = TimeUnit.MINUTES.toMillis(30);

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get cache, name=" + name);

        Cache<K, V> cache = caches.get(name);

        if (cache == null) {
//            TimedCache<K, V> timedCache = CacheUtil.newTimedCache(this.expire);
            SiteTimedCache<K, V> timedCache = new SiteTimedCache<>(this.expire);
            timedCache.schedulePrune(5);
            cache = new MemoryCache<>(timedCache);
            caches.put(name, cache);
        }
        return cache;
    }
}
