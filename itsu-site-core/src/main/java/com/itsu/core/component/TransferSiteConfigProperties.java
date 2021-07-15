package com.itsu.core.component;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jerry.Su
 * @Date 2021/7/14 14:24
 * 平台配置类转换类
 */
public class TransferSiteConfigProperties {

    @Autowired
    private ItsuSiteConfigProperties config;

    public ItsuSiteConfigProperties getConfig() {
        return config;
    }

    public void setConfig(ItsuSiteConfigProperties config) {
        this.config = config;
    }
}
