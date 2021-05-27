package com.itsu.site.framework.config.importselector;

import com.itsu.core.component.validate.RequestParamValidate;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Jerry Su
 * @Date 2021/5/27 16:58
 */
public class GlobalParamCheckImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{RequestParamValidate.class.getName()};
    }
}
