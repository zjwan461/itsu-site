package com.itsu.site.framework.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsu.core.api.AccountService;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.entity.Account;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.shiro.AesUsernamePasswordToken;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.util.TimeUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.LoginReqVo;
import com.itsu.core.vo.io.resp.LoginRespVo;
import com.itsu.site.framework.mapper.AccountMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.annotation.Resource;

/**
 * @author Jerry Su
 * @Date 2021-01-22 14:49:31
 */
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private ItsuSiteConfigProperties configProperties;

    /**
     * 登录接口的实现
     */
    @Override
    public JsonResult login(LoginReqVo loginReqVo) throws CodeAbleException {
        UsernamePasswordToken token = null;
        if (SystemUtil.isLoginAesEncrypt()) {
            token = new AesUsernamePasswordToken(loginReqVo.getUsername(), loginReqVo.getPassword());
        } else {
            token = new UsernamePasswordToken(loginReqVo.getUsername(), loginReqVo.getPassword());
        }
        SecurityUtils.getSubject().login(token);
        QueryWrapper<Account> condition = new QueryWrapper<>();
        condition.eq("username", token.getUsername());
        Account user = accountMapper.selectOne(condition);
        user.setLastLoginTime(DateUtil.now());
        accountMapper.updateById(user);
        LoginRespVo data = new LoginRespVo();
        data.setAccessToken(JWTUtil.sign(user.getUsername(), user.getPassword(), TimeUtil.toMillis(configProperties.getAccessToken().getExpire())));
        return JsonResult.ok(data);
    }

    @Override
    public JsonResult logout() throws CodeAbleException {
        return JsonResult.ok();
    }

}
