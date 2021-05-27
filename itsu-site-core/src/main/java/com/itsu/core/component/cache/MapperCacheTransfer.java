/*
 * @Author: Jerry Su
 * @Date: 2020-12-25 09:16:47
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-25 15:56:55
 */
package com.itsu.core.component.cache;

import cn.hutool.cache.CacheUtil;
import com.itsu.core.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

public class MapperCacheTransfer {

    @Autowired(required = false)
    @Qualifier("jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取ioc中的redistemplate传入MybatisRedisCache
     */
    @PostConstruct
    public void transfer() {
        MapperCache.setRedisTemplate(redisTemplate);
        MapperCache.setTimedCache(CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(SystemUtil.getMapperCacheTime())));
        MapperCache.setCacheMinutes(SystemUtil.getMapperCacheTime());
        MapperCache.setCachePrefix(SystemUtil.getMapperCachePrefix());
    }


}