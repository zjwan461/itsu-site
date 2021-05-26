/*
 * @Author: Jerry Su
 * @Date: 2020-12-23 16:37:55
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-28 10:40:29
 */
package com.itsu.site.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class FilterErrorController implements BaseController {

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 将http filter中的异常抛出给自定义Spring异常拦截器
     *
     * @param request
     * @throws Throwable
     */
    @RequestMapping("/filterThrowException")
    public void handleError(HttpServletRequest request) throws Throwable {
        throw (Exception) request.getAttribute("filter.exception");
    }

}
