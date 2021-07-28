/*
 * @Author: Jerry Su
 * @Date: 2020-12-25 16:04:20
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-25 16:14:59
 */
package com.itsu.site.framework.shiro.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsu.core.entity.Account;
import com.itsu.core.shiro.JwtTokenFilter;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import com.itsu.site.framework.mapper.AccountMapper;

import javax.annotation.Resource;

public class ItsuSiteApiJwtTokenFilter extends JwtTokenFilter {

    @Resource
    private AccountMapper accountMapper;

    @Override
    protected Account getAccountInfoByUserName(String username) {
        QueryWrapper<Account> condition = new QueryWrapper<>();
        condition.eq(ItsuSiteConstant.USER_NAME, username).last(ItsuSiteConstant.LIMIT_ONE);
        return accountMapper.selectOne(condition);
    }

}
