/*
 * @Author: Jerry Su
 * @Date: 2020-12-23 15:20:34
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-25 15:56:51
 */
package com.itsu.core.component.cache;

import cn.hutool.cache.impl.TimedCache;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.util.SystemUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapperCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(MapperCache.class);

    private final String id;

    private static Integer CACHE_MINUTES = 30;

    private static RedisTemplate<String, Object> redisTemplate = null;

    private static TimedCache<String, Object> timedCache = null;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public MapperCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try {
            if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.REDIS) {
                redisTemplate.opsForValue().set(CACHE_PREFIX + key, value, CACHE_MINUTES, TimeUnit.MINUTES);
            } else if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.MEMORY) {
                timedCache.put(CACHE_PREFIX + key, value, TimeUnit.MINUTES.toMillis(CACHE_MINUTES));
            }
        } catch (Exception e) {
            logger.warn("put object to cache fail [{}]", e.getMessage());
        }
    }

    @Override
    public Object getObject(Object key) {
        Object object = null;
        try {
            if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.REDIS) {
                object = redisTemplate.opsForValue().get(CACHE_PREFIX + key);
            } else if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.MEMORY) {
                object = timedCache.get(CACHE_PREFIX + key);
            }
        } catch (Exception e) {
            logger.warn("get object from cache fail [{}] ", e.getMessage());
        }
        return object;
    }

    @Override
    public Object removeObject(Object key) {
        Object object = null;
        if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.REDIS) {
            object = redisTemplate.delete(CACHE_PREFIX + key);
        } else if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.MEMORY) {
            object = getObject(key);
            timedCache.remove(CACHE_PREFIX + key);
        }
        return object;
    }

    @Override
    public void clear() {
        try {
            if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.REDIS) {
                Set<String> keys = redisTemplate.keys(CACHE_PREFIX + "*");
                redisTemplate.delete(keys);
            } else if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.MEMORY) {
                timedCache.clear();
            }
        } catch (Exception e) {
            logger.warn("clear cache fail [{}]", e.getMessage());
        }

    }

    @Override
    public int getSize() {
        int size = 0;
        try {
            if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.REDIS) {
                size = redisTemplate.keys(CACHE_PREFIX + "*").size();
            } else if (SystemUtil.getMapperCacheType() == ItsuSiteConfigProperties.MapperCache.CacheType.MEMORY) {
                size = timedCache.size();
            }
        } catch (Exception e) {
            logger.warn("get cache size fail [{}]", e.getMessage());
        }
        return size;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

    public static RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        MapperCache.redisTemplate = redisTemplate;
    }

    public static TimedCache<String, Object> getTimedCache() {
        return timedCache;
    }

    public static void setTimedCache(TimedCache<String, Object> timedCache) {
        MapperCache.timedCache = timedCache;
    }

    public static void setCachePrefix(String cachePrefix) {
        CACHE_PREFIX = cachePrefix;
    }

    private static String CACHE_PREFIX = "mybatis:cache:";

    public static String getCachePrefix() {
        return CACHE_PREFIX;
    }

    public static Integer getCacheMinutes() {
        return CACHE_MINUTES;
    }

    public static void setCacheMinutes(Integer cacheMinutes) {
        CACHE_MINUTES = cacheMinutes;
    }

}