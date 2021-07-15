package com.itsu.site.framework.config.annotation.condition;

import com.itsu.site.framework.config.condition.ShiroCachingMemoryCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author Jerry.Su
 * @Date 2021/7/15 16:51
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ShiroCachingMemoryCondition.class)
public @interface ConditionalOnShiroCacheMemory {
}
