package com.itsu.site.framework.config.importselector;

import com.itsu.core.component.validate.GlobalRequestParamValidate;
import com.itsu.core.context.ApplicationContext;
import com.itsu.core.context.DefaultApplicationContext;
import com.itsu.site.framework.config.annotation.enable.EnableGlobalParamCheck;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author Jerry Su
 * @Date 2021/5/27 16:58
 */
public class GlobalParamCheckImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableGlobalParamCheck.class.getName());
        boolean enable = (boolean) attributes.get("enable");
        String[] regExs = (String[]) attributes.get("regExs");
        ApplicationContext dac = DefaultApplicationContext.getInstance();
        if (enable) {
            dac.set("globalParamCheck", true);
        }
        dac.set("regExs", regExs);
        return new String[]{GlobalRequestParamValidate.class.getName()};
    }
}
