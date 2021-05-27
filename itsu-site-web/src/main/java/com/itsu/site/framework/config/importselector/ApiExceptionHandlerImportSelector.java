package com.itsu.site.framework.config.importselector;

import com.itsu.site.framework.controller.handler.ApiExceptionHandler;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ApiExceptionHandlerImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ApiExceptionHandler.class.getName()};
    }
}
