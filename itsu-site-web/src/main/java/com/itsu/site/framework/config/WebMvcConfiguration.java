/*
 * @Author: Jerry Su
 * @Date: 2020-12-25 10:02:23
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-29 10:43:49
 */
package com.itsu.site.framework.config;

import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.mvc.CrossOriginFilter;
import com.itsu.core.component.mvc.ExceptionThrowFilter;
import com.itsu.core.component.mvc.MaskJackson2HttpMessageConverter;
import com.itsu.core.component.mvc.RequestInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements InitializingBean {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(ItsuSiteConfigProperties kProperties) {
        return new WebMvcConfigurer() {

            /**
             * 添加通用请求拦截器
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new RequestInterceptor(kProperties.getName())).addPathPatterns("/**");
            }

            // /**
            // * 添加跨域支持
            // */
            // @Override
            // public void addCorsMappings(CorsRegistry registry) {
            // registry.addMapping("/**")
            // .allowedHeaders("access-control-allow-origin", "accesstoken", "Content-Type",
            // "X-Requested-With")
            // .allowCredentials(false).allowedMethods("*").allowedOrigins(kProperties.getAllowOrigins())
            // .maxAge(3600);
            // }

            /**
             * 添加自定义message converter，序列化json写日志处理
             */
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                MaskJackson2HttpMessageConverter jsonMessageConverter = new MaskJackson2HttpMessageConverter();
                jsonMessageConverter
                        .setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
                converters.add(0, jsonMessageConverter);
            }

        };
    }

    /**
     * 添加跨域Filter
     *
     * @param crossOriginFilter
     * @return
     */
    @Bean
    @ConditionalOnBean(CrossOriginFilter.class)
    public FilterRegistrationBean<CrossOriginFilter> registerCrossOriginFilter(CrossOriginFilter crossOriginFilter) {
        FilterRegistrationBean<CrossOriginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(crossOriginFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("CrossOriginFilter");
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registrationBean;
    }

    /**
     * 添加Filter异常拦截Filter
     *
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<ExceptionThrowFilter> registerFilterExceptionFilter(ExceptionThrowFilter filter) {
        FilterRegistrationBean<ExceptionThrowFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("ExceptionThrowFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Autowired
    private DispatcherServlet servlet;

    /**
     * 抛出404异常
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        servlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
