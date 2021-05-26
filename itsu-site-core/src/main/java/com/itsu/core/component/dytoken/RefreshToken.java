/*
 * @Author: Jerry Su
 * @Date: 2021-02-07 10:12:42
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-07 14:30:09
 */
package com.itsu.core.component.dytoken;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RefreshToken {

    boolean exceptionHandler() default false;

    String reason() default "it is a exception handle method";
}
