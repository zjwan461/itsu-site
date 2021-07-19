package com.itsu.site.framework.config.importselector;

import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.framework.DefaultApplicationContext;
import com.itsu.site.framework.config.annotation.enable.EnableApiExceptionHandler;
import com.itsu.site.framework.controller.handler.ApiExceptionHandler;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class ApiExceptionHandlerImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableApiExceptionHandler.class.getName());
        boolean enable = (boolean) attributes.get("enable");
        ApplicationContext dac = DefaultApplicationContext.getInstance();
        if (enable) {
            dac.set("ApiExceptionHandler", true);
        }
        return new String[]{ApiExceptionHandler.class.getName()};
    }
}
