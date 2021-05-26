package com.itsu.core.component.security;

import java.lang.annotation.*;

/**
 * @author Jerry Su
 * @Date 2021/5/26 11:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AccessStrategy {
    StrategyEnum value() default StrategyEnum.ALWAYS;
}
