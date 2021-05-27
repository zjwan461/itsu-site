package com.itsu.core.util;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.ResourceUtil;
import com.itsu.core.vo.sys.ErrorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ErrorPropertiesFactory {

    private ErrorPropertiesFactory() {
    }

    private static final ErrorProperties prop = new ErrorProperties();

    private static final Logger logger = LoggerFactory.getLogger(ErrorPropertiesFactory.class);

    public static ErrorProperties getObject() {
        if (prop.isEmpty()) {
            try {
                loadDefault(prop);
                loadCustom(prop);
            } catch (IOException e) {
                logger.error("load error code mapping properties fail ", e);
            }
        }
        return prop;
    }

    private static void loadDefault(ErrorProperties prop) throws IOException {
        prop.load(ResourceUtil.getStream("error_code_mapping.properties"));
    }

    private static void loadCustom(ErrorProperties prop) throws IOException {
        InputStream stream = null;
        try {
            stream = ResourceUtil.getStream(SystemUtil.getCustomErrorPropertiesPath());
        } catch (NoResourceException e) {
            logger.info("custom not provide a ErrorProperties");
        }
        if (stream != null)
            prop.load(stream);
    }

}
