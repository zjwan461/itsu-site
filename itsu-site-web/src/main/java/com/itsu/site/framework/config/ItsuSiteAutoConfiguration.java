package com.itsu.site.framework.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.itsu.core.api.ARPService;
import com.itsu.core.api.AccountService;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.TransferSiteConfigProperties;
import com.itsu.core.component.cache.MapperCacheTransfer;
import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.component.dytoken.RefreshTokenAspect;
import com.itsu.core.component.mvc.CrossOriginFilter;
import com.itsu.core.component.mvc.ExceptionThrowFilter;
import com.itsu.core.component.mvc.SpringMvcHelper;
import com.itsu.core.component.validate.GlobalRequestParamValidate;
import com.itsu.core.exception.InitialException;
import com.itsu.core.framework.ApplicationContext;
import com.itsu.core.framework.DefaultApplicationContext;
import com.itsu.core.util.ClassPathResourceUtil;
import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ErrorProperties;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import com.itsu.core.vo.sys.RefreshTokenType;
import com.itsu.site.framework.component.GenerateHtml;
import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import com.itsu.site.framework.component.ScriptProcess;
import com.itsu.site.framework.controller.ARPController;
import com.itsu.site.framework.controller.AccountLoginController;
import com.itsu.site.framework.controller.FilterErrorController;
import com.itsu.site.framework.controller.handler.ApiExceptionHandler;
import com.itsu.site.framework.controller.handler.ApiExceptionHandlerBase;
import com.itsu.site.framework.service.ARPServiceImpl;
import com.itsu.site.framework.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    @PostConstruct
    public void init() {
        System.out.println(ItsuSiteConstant.PLATFORM_BANNER);
    }

    @Bean
    public ApplicationContext dac() {
        return DefaultApplicationContext.getInstance();
    }

    @Bean
    public TransferSiteConfigProperties siteConfig() {
        TransferSiteConfigProperties siteConfig = new TransferSiteConfigProperties();
        siteConfig.setConfig(itsuSiteConfigProperties);
        return siteConfig;
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnBean(DispatcherServlet.class)
    public SpringMvcHelper springMvcHelper() {
        return new SpringMvcHelper();
    }

    @Bean
    @ConditionalOnMissingBean(ErrorProperties.class)
    public ErrorProperties errorProperties() {
        return ErrorPropertiesFactory.getObject();
    }

    @Bean
    @ConditionalOnProperty(name = "itsu.site.access-token.dynamic", havingValue = "true")
    @ConditionalOnMissingBean(RefreshTokenAspect.class)
    public RefreshTokenAspectAdaptor refreshTokenAspect() {
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
    @ConditionalOnMissingBean(GlobalRequestParamValidate.class)
    public GlobalRequestParamValidate globalRequestParamValidate() {
        return new GlobalRequestParamValidate();
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
    @DependsOn("errorProperties")
    public ApiExceptionHandler apiExceptionHandler() {
        return new ApiExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(CrossOriginFilter.class)
    public CrossOriginFilter crossOriginFilter() {
        return new CrossOriginFilter(itsuSiteConfigProperties);
    }

    @Bean
    public AccountLoginController accountLoginController() {
        return new AccountLoginController();
    }

    @Bean
    public ARPController arpController() {
        return new ARPController();
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
    @ConditionalOnMissingBean(ARPService.class)
    public ARPService arpService() {
        return new ARPServiceImpl();
    }

    @Bean
    public ExceptionThrowFilter exceptionThrowFilter() {
        return new ExceptionThrowFilter();
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
                            } catch (Exception e) {
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
                prepareSiteConfig();
                autoCreateTable();
                if (itsuSiteConfigProperties.isShowIocBeans()) {
                    System.out.println(ItsuSiteConstant.PLATFORM_NAME + " IOC contains beans: " + JSONUtil.toJsonPrettyStr(SpringUtil.getApplicationContext().getBeanDefinitionNames()));
                }
                if (itsuSiteConfigProperties.isShowMvcMapping()) {
                    System.out.println(ItsuSiteConstant.PLATFORM_NAME + " MVC mappings: [");
                    SpringMvcHelper helper = SpringUtil.getBean(SpringMvcHelper.class);
                    Map<RequestMappingInfo, HandlerMethod> handlerMethods = helper.getMapping().getHandlerMethods();
                    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                        RequestMappingInfo key = entry.getKey();
                        HandlerMethod handlerMethod = entry.getValue();
                        System.out.println("\t" + key.toString() + "=" + handlerMethod.toString());
                    }
                    System.out.println("]");

                }
                logger.info("{}:{} is started at {}", ItsuSiteConstant.PLATFORM_NAME + "-" + ItsuSiteConstant.PLATFORM_VERSION, SystemUtil.getProjectName(), DateUtil.now());
            }

            public void prepareSiteConfig() {
                ApplicationContext dac = SpringUtil.getBean(ApplicationContext.class);
                if (!itsuSiteConfigProperties.getApiExceptionHandler().isEnable()) {
                    boolean aeh = dac.get("ApiExceptionHandler") != null && (boolean) dac.get("ApiExceptionHandler");
                    if (aeh) {
                        itsuSiteConfigProperties.getApiExceptionHandler().setEnable(true);
                    }
                }
                if (!itsuSiteConfigProperties.getGlobalParamCheck().isEnable()) {
                    boolean gpc = dac.get("globalParamCheck") != null && (boolean) dac.get("globalParamCheck");
                    if (gpc) {
                        itsuSiteConfigProperties.getGlobalParamCheck().setEnable(true);
                    }
                    if (ArrayUtil.isNotEmpty(dac.get("regExs")) && dac.get("regExs") != null) {
                        itsuSiteConfigProperties.getGlobalParamCheck().setRegExs((String[]) dac.get("regExs"));
                    }
                }

                if (!itsuSiteConfigProperties.getAccessToken().isDynamic()) {
                    Object dynamic = dac.get("dynamic");
                    boolean isDy = dynamic != null && (boolean) dynamic;
                    if (isDy) {
                        itsuSiteConfigProperties.getAccessToken().setDynamic(true);
                    }
                    if (dac.get("refreshTokenType") == RefreshTokenType.MEMORY) {
                        itsuSiteConfigProperties.getAccessToken().setType(RefreshTokenType.MEMORY);
                    } else if (dac.get("refreshTokenType") == RefreshTokenType.REDIS) {
                        itsuSiteConfigProperties.getAccessToken().setType(RefreshTokenType.REDIS);
                    }
                    if (dac.get("expire") != null) {
                        itsuSiteConfigProperties.getAccessToken().setExpire((String) dac.get("expire"));
                    }
                    if (dac.get("backUpNum") != null) {
                        itsuSiteConfigProperties.getAccessToken().setBackUpTokenNum((Integer) dac.get("backUpNum"));
                    }
                    if (dac.get("keyPrefix") != null) {
                        itsuSiteConfigProperties.getAccessToken().setKeyPrefix((String) dac.get("keyPrefix"));
                    }
                }

                if (itsuSiteConfigProperties.isShowConfigModel()) {
                    System.out.println(ItsuSiteConstant.PLATFORM_NAME + " configuration model content: " + JSONUtil.toJsonPrettyStr(itsuSiteConfigProperties));
                }
            }

            public void autoCreateTable() throws Exception {
                if (!itsuSiteConfigProperties.getAutoCreateDbTable().isEnable()) {
                    logger.info("auto create table is set to false");
                } else {
                    ItsuSiteConfigProperties.AutoCreateDbTable.Type type = itsuSiteConfigProperties.getAutoCreateDbTable().getType();
                    logger.info("auto create table is set to {}", type);
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
            }
        };
    }

}
