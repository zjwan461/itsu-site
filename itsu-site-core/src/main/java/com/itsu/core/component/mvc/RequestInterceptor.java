/*
 * @Author: Jerry Su
 * @Date: 2021-01-07 08:59:33
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-07 09:07:53
 */
package com.itsu.core.component.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jerry Su
 * @ClassName: RequestInterceptor.java
 * @Date 2020年12月22日 上午11:55:18
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    private final String projectName;

    public RequestInterceptor(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 记录开始请求的时间,写log
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        request.setAttribute("ReqStartMiles", System.currentTimeMillis());
        String query = request.getQueryString();
        if (StringUtils.hasText(query)) {
            log.info("----------{}-request: {} start with request query: {}", this.projectName, requestURI, query);
        } else {
            log.info("----------{}-request: {} start ----------", this.projectName, requestURI);
        }
        return true;
    }

    /**
     * 记录结束请求的时间,写log
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String requestURI = request.getRequestURI();
        Object obj = request.getAttribute("ReqStartMiles");
        if (obj != null) {
            long startMiles = (long) obj;
            long currentMiles = System.currentTimeMillis();
            log.info("----------{}-request: {} end, used:{} ms ----------", this.projectName, requestURI,
                    (currentMiles - startMiles));
        } else
            log.info("----------{}-request: {} end ----------", this.projectName, requestURI);

    }

}
