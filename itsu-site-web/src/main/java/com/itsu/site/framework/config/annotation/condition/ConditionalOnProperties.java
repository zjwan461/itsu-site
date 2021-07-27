package com.itsu.site.framework.config.annotation.condition;

import com.itsu.site.framework.config.condition.OnPropertiesCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author Jerry.Su
 * @Date 2021/7/27 8:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnPropertiesCondition.class)
public @interface ConditionalOnProperties {

    String[] keys() default {};

    String[] values() default {};

    String relation() default "and";

    boolean matchIfMissing() default false;
}
