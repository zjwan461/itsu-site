package com.itsu.site.framework.config.annotation.enable;

import com.itsu.site.framework.config.importselector.ApiExceptionHandlerImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ApiExceptionHandlerImportSelector.class)
public @interface EnableApiExceptionHandler {
    boolean enable() default true;
}
