package com.itsu.core.component.mvc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.event.listener.LoginListener;
import com.itsu.core.exception.SingleLoginException;
import com.itsu.core.context.ApplicationContext;
import com.itsu.core.util.LogUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
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
        Set<String> kickOutList = getKickOutList();
        if (CollUtil.contains(kickOutList, ServletUtil.getHeader(request, ItsuSiteConstant.ACCESS_TOKEN, ItsuSiteConstant.SYSTEM_ENCODING))) {
            LogUtil.info(SingleLoginInterceptor.class, "account login twice or more");
            throw new SingleLoginException();
        }
        return true;
    }

    private Set<String> getKickOutList() {
        if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
            ApplicationContext ac = SpringUtil.getBean(ApplicationContext.class);
            return (Set<String>) ac.get(LoginListener.KICK_OUT_ATTR);
        } else if (SystemUtil.getSecurityCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
            RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("jsonRedisTemplate");
            return (Set<String>) redisTemplate.opsForValue().get(LoginListener.KICK_OUT_ATTR);
        } else {
            LogUtil.error(SingleLoginInterceptor.class, "not support cache Type{}", SystemUtil.getSecurityCacheType());
            return new HashSet<String>();
        }
    }

    protected boolean isSkip(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accesstoken = ServletUtil.getHeader(request, ItsuSiteConstant.ACCESS_TOKEN, ItsuSiteConstant.SYSTEM_ENCODING);
        return !StringUtils.hasText(accesstoken);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogUtil.debug(SingleLoginInterceptor.class, "finish to check singleLogin");
    }
}
