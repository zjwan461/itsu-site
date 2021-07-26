package com.itsu.core.component.event.listener;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 16:10
 */
public class RedisLogoutListener extends LogoutListener {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void removeAccount(String username) {
        redisTemplate.delete("Account:" + username);
    }
}
