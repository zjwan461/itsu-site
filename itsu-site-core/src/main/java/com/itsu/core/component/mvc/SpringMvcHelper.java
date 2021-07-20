package com.itsu.core.component.mvc;

import com.itsu.core.util.AopTargetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author Jerry Su
 * @Date 2021/5/26 13:56
 */
public class SpringMvcHelper {

    private static final Logger logger = LoggerFactory.getLogger(SpringMvcHelper.class);


    @Resource
    private RequestMappingHandlerMapping mapping;

    public RequestMappingHandlerMapping getMapping() {
        return mapping;
    }

    public HandlerMethod getHandlerMethod(HttpServletRequest request) {
        try {
            HandlerExecutionChain handler = mapping.getHandler(request);
            assert handler != null;
            Object obj = handler.getHandler();
            if (obj instanceof HandlerMethod)
                return (HandlerMethod) obj;
            else
                logger.warn("current request Handler is not a HandlerMethod");
        } catch (Exception e) {
            logger.debug("can not get the HandlerMethod for current request, maybe current requestURI [{}] not provided requestURI by system", request.getRequestURI());
        }
        return null;
    }

    public Object getHandlerMethodBean(HttpServletRequest request) throws Exception {
        HandlerMethod handlerMethod = this.getHandlerMethod(request);
        if (handlerMethod == null) {
            return null;
        }
        return AopTargetUtil.getTarget(handlerMethod.getBean());
    }

    public Class getHandlerMethodBeanType(HttpServletRequest request) throws Exception {
        Object bean = this.getHandlerMethodBean(request);
        return bean == null ? null : bean.getClass();
    }

    public Annotation[] getHandlerMethodBeanAnnotations(HttpServletRequest request) throws Exception {
        Class clazz = this.getHandlerMethodBeanType(request);
        if (clazz == null) {
            return null;
        }
        return clazz.getAnnotations();
    }

    public Annotation getHandlerMethodBeanAnnotation(HttpServletRequest request, Class<? extends Annotation> annotationClass) throws Exception {
        Class clazz = this.getHandlerMethodBeanType(request);
        if (clazz == null) {
            return null;
        }
        return clazz.getAnnotation(annotationClass);
    }

    public Annotation[] getHandlerMethodAnnotations(HttpServletRequest request) {
        HandlerMethod handlerMethod = this.getHandlerMethod(request);
        if (handlerMethod == null) {
            return null;
        }
        return handlerMethod.getMethod().getAnnotations();
    }

    public Annotation getHandlerMethodAnnotation(HttpServletRequest request, Class<? extends Annotation> annotationClass) {
        HandlerMethod handlerMethod = this.getHandlerMethod(request);
        if (handlerMethod == null) {
            return null;
        }
        return handlerMethod.getMethodAnnotation(annotationClass);
    }

}
