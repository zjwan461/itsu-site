package com.itsu.core.component.mvc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.event.listener.LoginListener;
import com.itsu.core.exception.SingleLoginException;
import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.util.LogUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 10:08
 */
public class SingleLoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isSkip(request, response, handler)) {
            return true;
        }
        ApplicationContext ac = SpringUtil.getBean(ApplicationContext.class);
        Set<String> kickOutList = (Set<String>) ac.get(LoginListener.KICK_OUT_ATTR);
        if (CollUtil.contains(kickOutList, ServletUtil.getHeader(request, "accesstoken", "utf-8"))) {
            LogUtil.info(SingleLoginInterceptor.class, "account login twice or more");
            throw new SingleLoginException();
        }
        return true;
    }

    protected boolean isSkip(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accesstoken = ServletUtil.getHeader(request, "accesstoken", "utf-8");
        return !StringUtils.hasText(accesstoken);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogUtil.debug(SingleLoginInterceptor.class, "finish to check singleLogin");
    }
}
