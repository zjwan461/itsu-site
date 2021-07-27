package com.itsu.core.component.event.listener;

import com.itsu.core.context.ApplicationContext;
import com.itsu.core.util.LogUtil;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 15:56
 */
public class DefaultLoginListener extends LoginListener {

    @Resource
    private ApplicationContext ac;

    @Override
    protected void saveTokens(String username, Set<String> tokens) {
        ac.set("Account:" + username, tokens);
    }

    @Override
    protected Set<String> getOldTokens(String username) {
        return (Set<String>) ac.get("Account:" + username);
    }

    @Override
    protected void kickOut(Set<String> tokens) {
        Object obj = ac.get(KICK_OUT_ATTR);
        if (obj instanceof Set) {
            Set<String> kickOutList = (Set<String>) obj;
            kickOutList.addAll(tokens);
        } else
            LogUtil.error(DefaultLoginListener.class, "kick out token list is not initial");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> kickOutList = new HashSet<>();
        ac.set(KICK_OUT_ATTR, kickOutList);
    }
}
