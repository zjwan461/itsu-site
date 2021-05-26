package com.itsu.site.framework.config;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.api.AccountService;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.component.dytoken.RefreshTokenAspect;
import com.itsu.core.component.mvc.CorsFilter;
import com.itsu.core.component.mvc.ExceptionThrowFilter;
import com.itsu.core.component.mvc.SpringMvcHelper;
import com.itsu.core.component.validate.RequestParamValidate;
import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.vo.sys.ErrorProperties;
import com.itsu.site.framework.component.GenerateHtml;
import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import com.itsu.site.framework.component.ScriptProcess;
import com.itsu.site.framework.controller.AccountLoginController;
import com.itsu.site.framework.controller.FilterErrorController;
import com.itsu.site.framework.controller.handler.ApiExceptionHandler;
import com.itsu.site.framework.service.AccountServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:19
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableConfigurationProperties(ItsuSiteConfigProperties.class)
public class ItsuSiteAutoConfiguration {

    private final ItsuSiteConfigProperties itsuSiteConfigProperties;

    public ItsuSiteAutoConfiguration(ItsuSiteConfigProperties itsuSiteConfigProperties) {
        this.itsuSiteConfigProperties = itsuSiteConfigProperties;
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnMissingBean(ErrorProperties.class)
    public ErrorProperties errorProperties() {
        return ErrorPropertiesFactory.getObject();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.access-token.dynamic", havingValue = "true", matchIfMissing = false)
    public RefreshTokenAspect refreshTokenAspect() {
        return new RefreshTokenAspectAdaptor();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.access-token.dynamic", havingValue = "true", matchIfMissing = false)
    public LocalTokenBlackList localTokenBlackList() {
        return new LocalTokenBlackList();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.global-param-check.enable", havingValue = "true", matchIfMissing = false)
    public RequestParamValidate requestParamValidate() {
        return new RequestParamValidate();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.generate-html.enable", havingValue = "true")
    public GenerateHtml generateHtml() {
        return new GenerateHtml();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.script-process.enable", havingValue = "true")
    public ScriptProcess scriptProcess() {
        return new ScriptProcess();
    }


    @Bean
    public ApiExceptionHandler apiExceptionHandler() {
        return new ApiExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(CorsFilter.class)
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public AccountLoginController accountLoginController() {
        return new AccountLoginController();
    }

    @Bean
    public FilterErrorController filterErrorController() {
        return new FilterErrorController();
    }

    @Bean
    @ConditionalOnMissingBean(AccountService.class)
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public ExceptionThrowFilter exceptionThrowFilter() {
        return new ExceptionThrowFilter();
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnBean(DispatcherServlet.class)
    public SpringMvcHelper springMvcHelper() {
        return new SpringMvcHelper();
    }
}
