package com.itsu.core.component.event.listener;

import cn.hutool.core.date.DateUtil;
import com.itsu.core.component.event.LogoutEvent;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @author Jerry Su
 * @Date 2021/7/24 19:09
 */
public abstract class LogoutListener implements ApplicationListener<LogoutEvent> {

    @Override
    public void onApplicationEvent(LogoutEvent event) {
        String accesstoken = (String) event.getSource();
        long timestamp = event.getTimestamp();
        if (!StringUtils.hasText(accesstoken)) {
            return;
        }
        String username = JWTUtil.getUsername(accesstoken);
        if (SystemUtil.isSingleLoginEnable()) {
            removeAccount(username);
        }
        LogUtil.info(LogoutListener.class, "Account:{} was logout at {}", username, DateUtil.date(timestamp));
    }

    protected abstract void removeAccount(String username);

}
