package com.itsu.core.component.event.schedue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.itsu.core.component.event.listener.LoginListener;
import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.LogUtil;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 10:48
 */
public class ApplicationContextCleaner {

    @Resource
    private ApplicationContext ac;

    @Scheduled(fixedRateString = "#{siteConfig.config.securityConfig.singleLoginCheckTime}")
    public void clean() {
        Set<String> kickOutList = (Set<String>) ac.get(LoginListener.KICK_OUT_ATTR);
        if (CollUtil.isNotEmpty(kickOutList)) {
            cleanTokens(kickOutList);
        }

        Set<String> loginAccounts = ac.keys().stream().filter(key -> key.startsWith("Account")).collect(Collectors.toSet());
        for (String accountKey : loginAccounts) {
            Set<String> accountTokens = (Set<String>) ac.get(accountKey);
            if (CollUtil.isNotEmpty(accountTokens)) {
                cleanTokens(accountTokens);
            } else
                ac.remove(accountKey);
        }
    }

    protected void cleanTokens(Set<String> tokenList) {
        Iterator<String> it = tokenList.iterator();
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
            }
        }
    }
}
