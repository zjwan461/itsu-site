package com.itsu.site.framework.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.api.AccountService;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.cache.MapperCacheTransfer;
import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.component.dytoken.RefreshTokenAspect;
import com.itsu.core.component.mvc.CorsFilter;
import com.itsu.core.component.mvc.ExceptionThrowFilter;
import com.itsu.core.component.mvc.SpringMvcHelper;
import com.itsu.core.component.validate.RequestParamValidate;
import com.itsu.core.exception.InitialException;
import com.itsu.core.util.ClassPathResourceUtil;
import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ErrorProperties;
import com.itsu.site.framework.component.GenerateHtml;
import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import com.itsu.site.framework.component.ScriptProcess;
import com.itsu.site.framework.controller.AccountLoginController;
import com.itsu.site.framework.controller.FilterErrorController;
import com.itsu.site.framework.controller.handler.ApiExceptionHandler;
import com.itsu.site.framework.controller.handler.ApiExceptionHandlerBase;
import com.itsu.site.framework.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.IOException;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:19
 */
@Configuration
@ConditionalOnProperty(name = "itsu.site.enable", havingValue = "true", matchIfMissing = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableConfigurationProperties(ItsuSiteConfigProperties.class)
@Import({MybatisPlusConfiguration.class, RedisConfiguration.class, ShiroConfiguration.class, WebMvcConfiguration.class})
public class ItsuSiteAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ItsuSiteAutoConfiguration.class);

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
    @DependsOn("springUtil")
    public ErrorProperties errorProperties() {
        return ErrorPropertiesFactory.getObject();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.access-token.dynamic", havingValue = "true", matchIfMissing = false)
    @ConditionalOnMissingBean(RefreshTokenAspect.class)
    public RefreshTokenAspect refreshTokenAspect() {
        return new RefreshTokenAspectAdaptor();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.access-token.dynamic", havingValue = "true")
    @ConditionalOnExpression("'${itsu.site.access-token.type}'.equalsIgnoreCase('MEMORY')")
    @ConditionalOnMissingBean(LocalTokenBlackList.class)
    public LocalTokenBlackList localTokenBlackList() {
        return new LocalTokenBlackList();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.global-param-check.enable", havingValue = "true")
    @ConditionalOnMissingBean(RequestParamValidate.class)
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
    @ConditionalOnProperty(name = "itsu.site.api-exception-handler.enable", havingValue = "true")
    @ConditionalOnMissingBean(ApiExceptionHandlerBase.class)
    public ApiExceptionHandlerBase apiExceptionHandler() {
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

    @Bean
    public MapperCacheTransfer mapperCacheTransfer() {
        return new MapperCacheTransfer();
    }


    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            private JdbcTemplate jdbcTemplate;

            public void execute(String classpath) throws IOException {
                try {
                    File file = ClassPathResourceUtil.getFile(classpath);
                    String content = FileUtil.readString(file, "UTF-8");
                    if (StrUtil.isBlank(content)) {
                        logger.warn("empty content for create.sql, will skip auto create/update table");
                    } else {
                        String[] sqls = StrUtil.split(content, ";");
                        for (String sql : sqls) {
                            try {
                                if (StrUtil.isNotBlank(sql))
                                    jdbcTemplate.execute(sql);
                            } catch (DataAccessException e) {
                                logger.warn("error execute sql:[{}], error message:[{}]", sql, e.getMessage());
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.warn("auto create table contains errors", e);
                    throw e;
                }
            }

            @Override
            public void run(ApplicationArguments args) throws Exception {
                if (!itsuSiteConfigProperties.getAutoCreateDbTable().isEnable()) {
                    logger.info("auto create table is set to false");
                } else {
                    ItsuSiteConfigProperties.AutoCreateDbTable.Type type = itsuSiteConfigProperties.getAutoCreateDbTable().getType();
                    if (type == ItsuSiteConfigProperties.AutoCreateDbTable.Type.CREATE) {
                        execute("classpath:schema/create.sql");
                    } else if (type == ItsuSiteConfigProperties.AutoCreateDbTable.Type.UPDATE) {
                        execute("classpath:schema/drop.sql");
                        execute("classpath:schema/create.sql");
                    } else {
                        throw new InitialException("not supported auto create type [" + type.name() + "]");
                    }

                    if (itsuSiteConfigProperties.getAutoCreateDbTable().isInitData()) {
                        execute("classpath:schema/init-data.sql");
                    }
                }
                logger.info("itsu-site application:{} is started at {}", SystemUtil.getProjectName(), DateUtil.now());
            }
        };
    }

}
