package com.itsu.core.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/14 17:34
 */
public class MemoryCache<K, V> implements Cache<K, V> {

    private volatile SiteTimedCache<K, V> timedCache = null;

    public MemoryCache(SiteTimedCache<K, V> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public V get(K k) throws CacheException {
        return timedCache.get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        timedCache.put(k, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = get(k);
        timedCache.remove(k);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        timedCache.clear();
    }

    @Override
    public int size() {
        return timedCache.size();
    }

    @Override
    public Set<K> keys() {
        return timedCache.getKeys();
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        timedCache.forEach(v -> {
            list.add(v);
        });
        return list;
    }
}
