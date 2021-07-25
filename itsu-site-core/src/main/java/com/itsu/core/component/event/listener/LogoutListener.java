package com.itsu.core.component.event.listener;

import com.itsu.core.component.event.LogoutEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Jerry Su
 * @Date 2021/7/24 19:09
 */
public class LogoutListener implements ApplicationListener<LogoutEvent> {
    @Override
    public void onApplicationEvent(LogoutEvent event) {

    }
}
