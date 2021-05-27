package com.itsu.site.framework.config.importselector;

import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Jerry Su
 * @Date 2021/5/27 17:33
 */
public class RefreshTokenImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{RefreshTokenAspectAdaptor.class.getName()};
    }
}
