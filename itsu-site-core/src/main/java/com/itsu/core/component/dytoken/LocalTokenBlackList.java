/*
 * @Author: Jerry Su 
 * @Date: 2021-02-07 10:12:31 
 * @Last Modified by:   Jerry Su 
 * @Last Modified time: 2021-02-07 10:12:31 
 */
package com.itsu.core.component.dytoken;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.util.TimeUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;

public class LocalTokenBlackList implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -7091248003525441448L;

    @Resource
    private ItsuSiteConfigProperties configProperties;

    private volatile TimedCache<String, String> timedCache = null;

    @PostConstruct
    public void init() {
        timedCache = CacheUtil.newTimedCache(TimeUtil.toMillis(configProperties.getAccessToken().getExpire()));
        timedCache.schedulePrune(5);
    }

    public TimedCache<String, String> getCache() {
        return timedCache;
    }

    public void pushToken(String token, String username) {
        timedCache.put(token, username);
    }

    public String getToken(String token) {
        return timedCache.get(token, false);
    }

}
