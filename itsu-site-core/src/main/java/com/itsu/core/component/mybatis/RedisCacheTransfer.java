/*
 * @Author: Jerry Su 
 * @Date: 2020-12-25 09:16:47 
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-25 15:56:55
 */
package com.itsu.core.component.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheTransfer {

    /**
     * 获取ioc中的redistemplate传入MybatisRedisCache
     * 
     * @param redisTemplate
     */
    @Autowired
    public void setRedisTemplate(@Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }
}