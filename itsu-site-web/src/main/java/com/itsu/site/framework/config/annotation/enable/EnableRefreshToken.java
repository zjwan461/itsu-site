package com.itsu.site.framework.config.annotation.enable;

import com.itsu.core.component.dytoken.RefreshTokenAspect;
import com.itsu.core.vo.sys.RefreshTokenType;
import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import com.itsu.site.framework.config.importselector.RefreshTokenImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Jerry Su
 * @Date 2021/5/27 16:57
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RefreshTokenImportSelector.class)
public @interface EnableRefreshToken {
    RefreshTokenType type() default RefreshTokenType.MEMORY;

    Class<? extends RefreshTokenAspect> customRefreshTokenAspect() default RefreshTokenAspectAdaptor.class;
}
