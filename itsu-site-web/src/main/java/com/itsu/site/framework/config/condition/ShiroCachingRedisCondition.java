package com.itsu.site.framework.config.condition;

import cn.hutool.json.JSONUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.TransferSiteConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 16:45
 */
public class ShiroCachingRedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        System.out.println(JSONUtil.toJsonPrettyStr(context.getBeanFactory().getBeanDefinitionNames()));
        TransferSiteConfigProperties siteConfig = (TransferSiteConfigProperties) context.getBeanFactory().getBean("siteConfig");
        ItsuSiteConfigProperties.SecurityConfig securityConfig = siteConfig.getConfig().getSecurityConfig();
        return securityConfig.isCacheEnable() || securityConfig.getCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS;
    }
}
