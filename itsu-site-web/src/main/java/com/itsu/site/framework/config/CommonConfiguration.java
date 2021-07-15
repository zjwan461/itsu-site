package com.itsu.site.framework.config;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.TransferSiteConfigProperties;
import com.itsu.core.component.mvc.SpringMvcHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 16:31
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonConfiguration {

    @Resource
    private ItsuSiteConfigProperties itsuSiteConfigProperties;

    @Bean
    public TransferSiteConfigProperties siteConfig() {
        TransferSiteConfigProperties siteConfig = new TransferSiteConfigProperties();
        siteConfig.setConfig(itsuSiteConfigProperties);
        return siteConfig;
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnBean(DispatcherServlet.class)
    public SpringMvcHelper springMvcHelper() {
        return new SpringMvcHelper();
    }
}
