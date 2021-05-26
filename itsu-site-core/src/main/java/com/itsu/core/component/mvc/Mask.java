/*
 * @Author: Jerry Su 
 * @Date: 2021-02-07 10:12:36 
 * @Last Modified by:   Jerry Su 
 * @Last Modified time: 2021-02-07 10:12:36 
 */
package com.itsu.core.component.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mask {
    String value() default "********";

    boolean logEnable() default true;

    boolean respEnable() default false;
}
