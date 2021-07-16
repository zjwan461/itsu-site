/*
 * @Author: Jerry Su
 * @Date: 2020-12-23 15:07:59
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-25 15:52:08
 */
package com.itsu.site.framework.config;

import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.shiro.AuthenRealmBase;
import com.itsu.core.shiro.JwtTokenFilter;
import com.itsu.core.shiro.MemoryCacheManager;
import com.itsu.core.shiro.StatelessDefaultSubjectFactory;
import com.itsu.site.framework.shiro.filter.ItsuSiteApiJwtTokenFilter;
import com.itsu.site.framework.shiro.realm.AuthenRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@AutoConfigureAfter(RedisConfiguration.class)
public class ShiroConfiguration {

    @Value("${itsu.site.name")
    private String applicationName;

    @Resource
    private ItsuSiteConfigProperties kProperties;

    /**
     * 开启注解权限角色控制
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator kolApp = new DefaultAdvisorAutoProxyCreator();
        kolApp.setProxyTargetClass(true);
        return kolApp;
    }

    /**
     * http请求路劲对应shiro filter配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ShiroFilterChainDefinition.class)
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/api/**", "jwt");
        chainDefinition.addPathDefinition("/anon/**", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        return chainDefinition;
    }

    /**
     * 自定义jwttokenfilter注入ioc
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(JwtTokenFilter.class)
    public JwtTokenFilter jwtTokenFilter() {
        return new ItsuSiteApiJwtTokenFilter();
    }

    /**
     * shiro基本设置，并添加自定义jwtfilter
     *
     * @param definitions
     * @param securityManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroFilterChainDefinition definitions,
                                                         SecurityManager securityManager, JwtTokenFilter jwtTokenFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", jwtTokenFilter);
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitions.getFilterChainMap());
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /**
     * 自定义无状态subjectFactory
     *
     * @return
     */
    @Bean
    public DefaultSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    /**
     * 不校验session
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultSessionManager shiroSessionManager = new DefaultSessionManager();
        // 关闭session校验轮询
        shiroSessionManager.setSessionValidationSchedulerEnabled(false);
        return shiroSessionManager;
    }

    /**
     * 定义securitymanager，指定认证realm； 指定缓存管理器为redis
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionsSecurityManager.class)
    public SecurityManager securityManager(RedisCacheManager redisCacheManager, MemoryCacheManager memoryCacheManager, AuthenRealmBase authenRealm
            , SessionManager sessionManager, SubjectDAO subjectDAO, SubjectFactory subjectFactory) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ThreadContext.bind(securityManager);
        securityManager.setRealms(Arrays.asList(authenRealm));
        if (kProperties.getSecurityConfig().isCacheEnable()) {
            if (kProperties.getSecurityConfig().getCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.MEMORY) {
                securityManager.setCacheManager(memoryCacheManager);
            } else if (kProperties.getSecurityConfig().getCacheType() == ItsuSiteConfigProperties.SecurityConfig.CacheType.REDIS) {
                securityManager.setCacheManager(redisCacheManager);
            }
        }
        securityManager.setSessionManager(sessionManager);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setSubjectFactory(subjectFactory);
        return securityManager;
    }

    /**
     * 自定义subjectDao不存储session
     *
     * @return
     */
    @Bean
    public SubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        return subjectDAO;
    }

    /**
     * 自定义认证realm注入ioc
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthenRealmBase.class)
    public AuthenRealmBase authenRealm(CredentialsMatcher credentialsMatcher) {
        AuthenRealm authenRealm = new AuthenRealm();
        authenRealm.setName(applicationName + "-realm");
        if (kProperties.getSecurityConfig().isCacheEnable()) {
            authenRealm.setAuthenticationCacheName(kProperties.getSecurityConfig().getAuthenticationCacheName());
            authenRealm.setAuthorizationCacheName(kProperties.getSecurityConfig().getAuthorizationCacheName());
            authenRealm.setCachingEnabled(true);
            authenRealm.setAuthorizationCachingEnabled(true);
            authenRealm.setAuthenticationCachingEnabled(true);
        }
        authenRealm.setCredentialsMatcher(credentialsMatcher);
        return authenRealm;
    }

    /**
     * 指定加密规则为md5以及迭代次数
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CredentialsMatcher.class)
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(kProperties.getSecurityConfig().getHashAlgorithmName());
        credentialsMatcher.setHashIterations(kProperties.getSecurityConfig().getHashIterations());
        return credentialsMatcher;
    }

    /**
     * rediscachemanager 注入ioc
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedisCacheManager.class)
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        redisCacheManager.setExpire(kProperties.getSecurityConfig().getCacheExpire());
        return redisCacheManager;
    }

    /**
     * redisManager 注入ioc
     *
     * @param jedisPoolConfig
     * @param redisProperties
     * @return
     */
    @Bean
    @ConditionalOnBean(RedisCacheManager.class)
    @ConditionalOnMissingBean(RedisManager.class)
    public RedisManager redisManager(JedisPoolConfig jedisPoolConfig, RedisProperties redisProperties) {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPoolConfig(jedisPoolConfig);
        redisManager.setHost(redisProperties.getHost() + ":" + redisProperties.getPort());
        redisManager.setPassword(redisProperties.getPassword());
        redisManager.setDatabase(redisProperties.getDatabase());
        return redisManager;
    }

    /**
     * 本地内存缓存
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MemoryCacheManager.class)
    public MemoryCacheManager memoryCacheManager() {
        MemoryCacheManager memoryCacheManager = new MemoryCacheManager();
        memoryCacheManager.setExpire(TimeUnit.SECONDS.toMillis(kProperties.getSecurityConfig().getCacheExpire()));
        return memoryCacheManager;
    }

    /**
     * 开启shiro注解权限控制
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 开启shiro生命周期
     *
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
