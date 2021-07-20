package com.itsu.core.component.mvc;

import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

/**
 * @author Jerry.Su
 * @Date 2021/7/20 17:20
 */
public class MaskIntrospector extends JacksonAnnotationIntrospector {

    private final boolean logEnable;

    private final boolean respEnable;

    public MaskIntrospector(boolean logEnable, boolean respEnable) {
        this.logEnable = logEnable;
        this.respEnable = respEnable;
    }

    @Override
    public boolean isAnnotationBundle(Annotation ann) {
        if (ann.annotationType().equals(MaskField.class)) {
            return false;
        } else
            return super.isAnnotationBundle(ann);

    }
}
