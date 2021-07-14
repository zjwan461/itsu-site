package com.itsu.site.framework.config.annotation.enable;

import com.itsu.site.framework.config.importselector.GlobalParamCheckImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Jerry Su
 * @Date 2021/5/27 16:57
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GlobalParamCheckImportSelector.class)
public @interface EnableGlobalParamCheck {

    String[] regExs() default {".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*"};
}
