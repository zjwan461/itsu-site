package com.itsu.core.component.event;

import org.springframework.context.ApplicationListener;

/**
 * @author Jerry.Su
 * @Date 2021/7/23 11:20
 */
public class LoginListener implements ApplicationListener<LoginEvent> {


    @Override
    public void onApplicationEvent(LoginEvent event) {
        Object source = event.getSource();
        long timestamp = event.getTimestamp();
        System.out.println(timestamp + ":" + source);
    }
}
