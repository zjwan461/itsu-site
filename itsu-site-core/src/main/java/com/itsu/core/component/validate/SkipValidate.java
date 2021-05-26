/*
 * @Author: Jerry Su 
 * @Date: 2021-02-07 10:12:51 
 * @Last Modified by:   Jerry Su 
 * @Last Modified time: 2021-02-07 10:12:51 
 */
package com.itsu.core.component.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipValidate {

}
