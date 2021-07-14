package com.itsu.core.shiro;

import cn.hutool.cache.impl.TimedCache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/14 17:34
 */
public class MemoryCache<K,V> implements Cache<K,V> {

    private volatile TimedCache<K, V> timedCache = null;

    public MemoryCache(TimedCache<K, V> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public V get(K k) throws CacheException {
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
