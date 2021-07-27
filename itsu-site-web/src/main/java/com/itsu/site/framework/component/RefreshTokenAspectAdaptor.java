/*
 * @Author: Jerry Su
 * @Date: 2021-01-27 11:09:30
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-27 11:12:46
 */
package com.itsu.site.framework.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.dytoken.RefreshTokenAspect;
import com.itsu.core.entity.Account;
import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.site.framework.mapper.AccountMapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RefreshTokenAspectAdaptor extends RefreshTokenAspect {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private ApplicationContext ac;

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 重新颁发token的实现
     */
    @Override
    protected List<String> newSign(String username) {
        QueryWrapper<Account> condition = new QueryWrapper<>();
        condition.eq("username", username).last("limit 1");
        Account account = accountMapper.selectOne(condition);
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < SystemUtil.getBackUpTokenNum(); i++) {
            tokens.add(JWTUtil.sign(username, account.getPassword(), SystemUtil.getAccessTokenExpire()));
        }
        if (SystemUtil.isSingleLoginEnable()) {
            handleSingleLogin(username, tokens);
        }
        return tokens;
    }

    protected void handleSingleLogin(String username, List<String> tokens) {
        if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
            Set<String> oldTokens = (Set<String>) ac.get("Account:" + username);
            oldTokens.addAll(tokens);
            ac.set("Account:" + username, oldTokens);
        } else if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            Set<String> oldTokens = (Set<String>) redisTemplate.opsForValue().get("Account:" + username);
            oldTokens.addAll(tokens);
            redisTemplate.opsForValue().set("Account:" + username, oldTokens, SystemUtil.getAccessTokenExpire(), TimeUnit.MILLISECONDS);
        } else {
            LogUtil.debug(RefreshTokenAspectAdaptor.class, "unsupported Security cache type:[" + SystemUtil.getSecurityCacheType() + "]");
        }
    }

}
