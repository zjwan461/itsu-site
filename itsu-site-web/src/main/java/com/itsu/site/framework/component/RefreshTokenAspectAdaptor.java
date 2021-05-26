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
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.TimeUtil;
import com.itsu.site.framework.mapper.AccountMapper;

import javax.annotation.Resource;

public class RefreshTokenAspectAdaptor extends RefreshTokenAspect {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private ItsuSiteConfigProperties configProperties;

    /**
     * 重新颁发token的实现
     */
    @Override
    protected String newSign(String username) {
        QueryWrapper<Account> condition = new QueryWrapper<>();
        condition.eq("username", username).last("limit 1");
        Account account = accountMapper.selectOne(condition);
        return JWTUtil.sign(username, account.getPassword(), TimeUtil.toMillis(configProperties.getAccessToken().getExpire()));
    }

}
