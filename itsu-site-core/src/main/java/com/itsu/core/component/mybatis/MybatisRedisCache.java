/*
 * @Author: Jerry Su 
 * @Date: 2020-12-23 15:20:34 
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-25 15:56:51
 */
package com.itsu.core.component.mybatis;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache {

    private final String id;

    private static final String CACHE_PREFIX = "mybatis:cache:";

    private static final Integer CACHE_MINUTES = 30;

    public static RedisTemplate<String, Object> redisTemplate = null;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public MybatisRedisCache(final String id) {
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
        redisTemplate.opsForValue().set(CACHE_PREFIX + key, value, CACHE_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public Object getObject(Object key) {
        return redisTemplate.opsForValue().get(CACHE_PREFIX + key);
    }

    @Override
    public Object removeObject(Object key) {
        return redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(CACHE_PREFIX + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public int getSize() {
        return redisTemplate.keys(CACHE_PREFIX + "*").size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

}