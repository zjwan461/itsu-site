/*
 * @Author: Jerry Su
 * @Date: 2021-02-02 17:33:15
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-02 17:38:29
 */
package com.itsu.site.framework.component;

import cn.hutool.core.io.IoUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.exception.CodeAbleException;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class GenerateHtml {

    @Resource
    private Configuration cfg;

    @Resource
    private ItsuSiteConfigProperties kProp;

    @PostConstruct
    public void init() {
        String generateHtmlPath = kProp.getGenerateHtml().getGenerateHtmlPath();
        File file = new File(generateHtmlPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 生成html使用自定义路径
     *
     * @param templateName
     * @param htmlName
     * @param path
     * @param data
     * @return
     * @throws CodeAbleException
     */
    public String generateHtml(String templateName, String htmlName, String path, Map<String, Object> data)
            throws CodeAbleException {
        try {
            Template template = cfg.getTemplate(templateName);
            File file = new File(path, htmlName);
            template.process(data, IoUtil.getUtf8Writer(new FileOutputStream(file)));
            return file.getPath();
        } catch (Exception e) {
            throw new CodeAbleException(10001, e.getMessage());
        }
    }

    /**
     * 生成html使用默认路径
     *
     * @param templateName
     * @param htmlName
     * @param data
     * @return
     * @throws CodeAbleException
     */
    public String generateHtml(String templateName, String htmlName, Map<String, Object> data) throws CodeAbleException {
        return generateHtml(templateName, htmlName, kProp.getGenerateHtml().getGenerateHtmlPath(), data);
    }

}
