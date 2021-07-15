package com.itsu.core.shiro;

import cn.hutool.cache.impl.TimedCache;

import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 11:44
 */
public class SiteTimedCache<K, V> extends TimedCache<K, V> {
    public SiteTimedCache(long timeout) {
        super(timeout);
    }

    protected Set<K> getKeys() {
        return cacheMap.keySet();
    }
}
