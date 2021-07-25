package com.itsu.core.component.event.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.itsu.core.component.event.LoginEvent;
import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/23 11:20
 */
public final class LoginListener implements ApplicationListener<LoginEvent>, InitializingBean {

    private static final Logger logger = LogUtil.getLogger(LoginListener.class);

    public static final String KICK_OUT_ATTR = "KICK_OUT_TOKEN_LIST";

    @Resource
    private ApplicationContext ac;

    @Override
    public void onApplicationEvent(LoginEvent event) {
        Map<String, Object> map = (Map<String, Object>) event.getSource();
        String username = (String) map.get("username");
        long timestamp = event.getTimestamp();
        logger.info("Account:{} login in System at {}", username, DateUtil.date(timestamp));
        Set<String> oldTokens = (Set<String>) ac.get(username);
        if (CollUtil.isNotEmpty(oldTokens)) {
            logger.info("Account:{} with token {} was already login in the system ...", username, oldTokens);
            if (!SystemUtil.isSingleLoginEnable()) {
                logger.debug("single login is not enabled, skip to kickOut account:{}", username);
            } else {
                kickOut(oldTokens);
            }
        } else {
            logger.debug("account: {} was not login before, current login event is normally", username);
        }
        Set<String> tokens = (Set<String>) map.get("tokens");
        ac.set(username, tokens);
    }

    protected void kickOut(Set<String> tokens) {
        Object obj = ac.get(KICK_OUT_ATTR);
        if (obj instanceof Set) {
            Set<String> kickOutList = (Set<String>) obj;
            kickOutList.addAll(tokens);
        } else
            logger.error("kick out token list is not initial");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> kickOutList = new HashSet<>();
        ac.set(KICK_OUT_ATTR, kickOutList);
    }
}
