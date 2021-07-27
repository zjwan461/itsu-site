package com.itsu.core.component.event.schedue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.event.listener.LoginListener;
import com.itsu.core.context.ApplicationContext;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 10:48
 */
public class ApplicationContextCleaner {

    @Resource
    private ApplicationContext ac;

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRateString = "#{siteConfig.config.securityConfig.singleLoginCheckTime}")
    public void clean() {
        Set<String> kickOutList = getTokenList(LoginListener.KICK_OUT_ATTR);
        if (CollUtil.isNotEmpty(kickOutList)) {
            cleanTokens(LoginListener.KICK_OUT_ATTR, kickOutList);
        }

        Set<String> loginAccounts = getLoginAccounts();
        for (String accountKey : loginAccounts) {
            Set<String> accountTokens = getTokenList(accountKey);
            if (CollUtil.isNotEmpty(accountTokens)) {
                cleanTokens(accountKey, accountTokens);
            } else
                removeAccount(accountKey);
        }
    }

    private void removeAccount(String accountKey) {
        if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
            ac.remove(accountKey);
        } else if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            redisTemplate.delete(accountKey);
        }
    }

    private Set<String> getLoginAccounts() {
        if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
            return ac.keys().stream().filter(key -> key.startsWith("Account:")).collect(Collectors.toSet());
        } else if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            return redisTemplate.keys("Account:");
        }
        return new HashSet<>();
    }

    private Set<String> getTokenList(String kickOutAttr) {
        if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
            return (Set<String>) ac.get(kickOutAttr);
        } else if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            return (Set<String>) redisTemplate.opsForValue().get(kickOutAttr);
        }
        return new HashSet<>();
    }

    protected void cleanTokens(String attr, Set<String> tokenList) {
        Iterator<String> it = tokenList.iterator();
        boolean change = false;
        while (it.hasNext()) {
            String token = it.next();
            Date expireTime = null;
            try {
                expireTime = JWTUtil.getExpireTime(token);
            } catch (IllegalArgumentException e) {
                LogUtil.debug(ApplicationContextCleaner.class, "can not get expireTime for {}", e.getMessage());
            }
            if (expireTime == null || DateUtil.compare(new Date(), expireTime) > 0) {
                tokenList.remove(token);
                change = true;
            }
        }

        if (change && SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            redisTemplate.opsForValue().set(attr, tokenList, SystemUtil.getAccessTokenExpire(), TimeUnit.MILLISECONDS);
        }

    }
}
