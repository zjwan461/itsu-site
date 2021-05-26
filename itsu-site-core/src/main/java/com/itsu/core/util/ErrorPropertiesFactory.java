package com.itsu.core.util;

import cn.hutool.core.io.resource.ResourceUtil;
import com.itsu.core.vo.sys.ErrorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ErrorPropertiesFactory {

    private ErrorPropertiesFactory() {
    }

    private static final ErrorProperties prop = new ErrorProperties();

    private static final Logger logger = LoggerFactory.getLogger(ErrorPropertiesFactory.class);

    public static ErrorProperties getObject() {
        if (prop.isEmpty()) {
            try {
                prop.load(ResourceUtil.getStream("error_code_mapping.properties"));
            } catch (IOException e) {
                logger.error("load error code mapping properties fail ", e);
            }
        }
        return prop;
    }
}
