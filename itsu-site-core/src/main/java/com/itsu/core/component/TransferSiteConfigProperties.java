package com.itsu.core.component;

/**
 * @author Jerry.Su
 * @Date 2021/7/14 14:24
 * 平台配置类转换类
 */
public class TransferSiteConfigProperties {

    private final ItsuSiteConfigProperties properties;

    public TransferSiteConfigProperties(ItsuSiteConfigProperties properties) {
        this.properties = properties;
    }

    public ItsuSiteConfigProperties getProperties() {
        return properties;
    }
}
