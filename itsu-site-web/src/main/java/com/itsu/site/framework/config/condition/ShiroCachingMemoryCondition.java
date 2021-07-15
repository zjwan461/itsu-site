package com.itsu.site.framework.config.condition;

import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.TransferSiteConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 16:51
 */
public class ShiroCachingMemoryCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ItsuSiteConfigProperties.SecurityConfig securityConfig = context.getBeanFactory().getBean(TransferSiteConfigProperties.class).getConfig().getSecurityConfig();
        return securityConfig.isCacheEnable() || securityConfig.getCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY;
    }
}
