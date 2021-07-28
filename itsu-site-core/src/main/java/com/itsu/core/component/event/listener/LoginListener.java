package com.itsu.core.component.event.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.itsu.core.component.event.LoginEvent;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

import java.util.Map;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/23 11:20
 */
public abstract class LoginListener implements ApplicationListener<LoginEvent>, InitializingBean {

    private static final Logger logger = LogUtil.getLogger(LoginListener.class);

    public static final String KICK_OUT_ATTR = "KICK_OUT_TOKEN_LIST";

    @Override
    public void onApplicationEvent(LoginEvent event) {
        Map<String, Object> map = (Map<String, Object>) event.getSource();
        String username = (String) map.get(ItsuSiteConstant.USER_NAME);
        long timestamp = event.getTimestamp();
        logger.info("Account:{} login in System at {}", username, DateUtil.date(timestamp));
        if (!SystemUtil.isSingleLoginEnable()) {
            logger.debug("single login is not enabled, skip to kickOut account:{}", username);
            return;
        }
        Set<String> oldTokens = getOldTokens(username);
        if (CollUtil.isNotEmpty(oldTokens)) {
            logger.info("Account:{} with token {} was already login in the system ...", username, oldTokens);
            kickOut(oldTokens);
        } else {
            logger.debug("account: {} was not login before, current login event is normally", username);
        }
        Set<String> tokens = (Set<String>) map.get("tokens");
        saveTokens(username, tokens);
    }

    protected abstract void saveTokens(String username, Set<String> tokens);

    protected abstract Set<String> getOldTokens(String username);

    protected abstract void kickOut(Set<String> tokens);

}
