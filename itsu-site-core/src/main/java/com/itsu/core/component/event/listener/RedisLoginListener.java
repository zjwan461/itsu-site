package com.itsu.core.component.event.listener;

import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 15:44
 */
public class RedisLoginListener extends LoginListener {

    private static final Logger logger = LogUtil.getLogger(RedisLoginListener.class);

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    protected void saveTokens(String username, Set<String> tokens) {
        redisTemplate.opsForValue().set(ItsuSiteConstant.SINGLE_LOGIN_ACCOUNT_PREFIX + username, tokens, SystemUtil.getAccessTokenExpire(), TimeUnit.MILLISECONDS);
    }

    @Override
    protected Set<String> getOldTokens(String username) {
        return (Set<String>) redisTemplate.opsForValue().get(ItsuSiteConstant.SINGLE_LOGIN_ACCOUNT_PREFIX + username);
    }

    @Override
    protected void kickOut(Set<String> tokens) {
        Object obj = redisTemplate.opsForValue().get(KICK_OUT_ATTR);
        if (obj instanceof Set) {
            Set<String> kickOutList = (Set<String>) obj;
            kickOutList.addAll(tokens);
            redisTemplate.opsForValue().set(KICK_OUT_ATTR, kickOutList);
        } else
            LogUtil.error(DefaultLoginListener.class, "kick out token list is not initial");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> kickOutList = new HashSet<>();
        redisTemplate.opsForValue().set(KICK_OUT_ATTR, kickOutList);
    }
}
