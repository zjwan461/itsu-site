/*
 * @Author: Jerry Su
 * @Date: 2020-12-25 10:25:27
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-25 15:19:11
 */
package com.itsu.site.framework.controller;


import com.itsu.core.api.AccountService;
import com.itsu.core.component.validate.SkipValidate;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.LoginReqVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountLoginController implements BaseController {

    @Resource
    private AccountService accountService;

    /**
     * 系统登录login处理
     *
     * @param user
     * @return
     * @throws CodeAbleException
     */
//    @PostMapping("${itsu.site.security-config.login-url}")
    @PostMapping("#{siteConfig.config.securityConfig.loginUrl}")
    @SkipValidate
    public JsonResult login(@RequestBody @Validated LoginReqVo user) throws CodeAbleException {
        return accountService.login(user);
    }

    /**
     * logout filter执行完成后默认会访问/路径
     *
     * @return
     * @throws CodeAbleException
     */
//    @GetMapping("${itsu.site.security-config.logout-url}")
    @GetMapping("#{siteConfig.config.securityConfig.logoutRedirect}")
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response) throws CodeAbleException {
        return accountService.logout(request, response);
    }

}
