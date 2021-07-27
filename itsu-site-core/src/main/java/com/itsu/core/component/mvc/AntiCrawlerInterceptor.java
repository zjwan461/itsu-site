/*
 * @Author: Jerry Su 
 * @Date: 2021-02-19 14:29:36 
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-19 16:51:54
 */
package com.itsu.core.component.mvc;

import com.itsu.core.component.ItsuSiteConfigProperties.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AntiCrawlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AntiCrawlerInterceptor.class);

    private final AntiCrawler antiCrawler;

    /**
     * @param antiCrawler
     */
    public AntiCrawlerInterceptor(AntiCrawler antiCrawler) {
        this.antiCrawler = antiCrawler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean result = true;
        if (CollUtil.isNotEmpty(antiCrawler.getIllegalUserAgents())) {
            result = checkUserAgent(ServletUtil.getHeaderIgnoreCase(request, ItsuSiteConstant.USER_AGENT));
        }
        if (result && StringUtils.hasText(antiCrawler.getReferer())) {
            result = checkReferer(ServletUtil.getHeaderIgnoreCase(request, ItsuSiteConstant.REFERER));
        }
        if (result && antiCrawler.getTsOffset() != null) {
            result = checkTs(ServletUtil.getHeaderIgnoreCase(request, ItsuSiteConstant.TS));
        }
        return result;
    }

    /**
     * 检查User-Agent
     * 
     * @param ua
     * @return
     */
    protected boolean checkUserAgent(String ua) {
        if (!StringUtils.hasText(ua)) {
            logger.info("AntiCrawler error found, request header do not have a User-Agent");
            return false;
        }
        for (String keyword : antiCrawler.getIllegalUserAgents()) {
            if (ua.toLowerCase().contains(keyword.toLowerCase())) {
                logger.info("AntiCrawler error found, request User-Agent[{}] is illegal", ua);
                return false;
            }
        }

        return true;
    }

    /**
     * 检查Referer
     * 
     * @param referer
     * @return
     */
    protected boolean checkReferer(String referer) {
        if (!StringUtils.hasText(referer)) {
            logger.info("AntiCrawler error found, request header do not have a Referer");
            return false;
        }
        if (!StrUtil.startWith(referer, antiCrawler.getReferer())) {
            logger.info("AntiCrawler error found, request Referer[{}] is illegal", referer);
            return false;
        }
        return true;
    }

    /**
     * 检查时间戳是否超出偏移量
     * 
     * @param ts
     * @return
     */
    protected boolean checkTs(String ts) {
        if (!StringUtils.hasText(ts)) {
            logger.info("AntiCrawler error found, request header do not have a ts");
            return false;
        }
        long tsNum = 0;
        try {
            tsNum = Long.parseLong(ts);
        } catch (Exception e) {
            logger.info("AntiCrawler error found, request ts[{}] is not a number", ts);
            return false;
        }
        long val = System.currentTimeMillis() - tsNum;
        if (val > antiCrawler.getTsOffset()) {
            logger.info("AntiCrawler error found, request ts[{}] is over offset", ts);
            return false;
        }

        return true;

    }

}
