package com.itsu.core.component.event.listener;

import com.itsu.core.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 16:01
 */
public class DefaultLogoutListener extends LogoutListener {

    @Resource
    private ApplicationContext ac;

    @Override
    protected void removeAccount(String username) {
        ac.remove("Account:" + username);
    }
}
