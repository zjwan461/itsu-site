package com.itsu.sample.controller;

import com.itsu.core.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jerry.Su
 * @Date 2021/7/16 16:40
 */
@RestController
@RequestMapping("/api")
public class AccessController {

    @GetMapping("/hello")
    @RequiresAuthentication
    public JsonResult<String> idx() {
        Subject subject = SecurityUtils.getSubject();
        return JsonResult.ok("hello itsu-site");
    }
}
