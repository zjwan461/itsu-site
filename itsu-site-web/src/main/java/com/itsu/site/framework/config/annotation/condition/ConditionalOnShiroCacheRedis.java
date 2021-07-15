package com.itsu.site.framework.config.annotation.condition;

import com.itsu.site.framework.config.condition.ShiroCachingRedisCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 16:43
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ShiroCachingRedisCondition.class)
public @interface ConditionalOnShiroCacheRedis {
}
