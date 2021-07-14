/*
 * @Author: Jerry Su
 * @Date: 2021-02-07 10:12:39
 * @Last Modified by:   Jerry Su
 * @Last Modified time: 2021-02-07 10:12:39
 */
package com.itsu.site.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.itsu.core.component.ItsuSiteConfigProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@MapperScan(basePackages = "com.itsu.site.framework.mapper")
public class MybatisPlusConfiguration {

    @Resource
    private ItsuSiteConfigProperties properties;

    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(properties.getPagination().getDbType());
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求 默认false
        paginationInnerInterceptor.setOverflow(properties.getPagination().isOverflow());
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(properties.getPagination().getMaxLimit());
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
