package com.itsu.core.component.mvc;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.ReqObjBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 8:52
 */
public class LogJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(LogJackson2HttpMessageConverter.class);

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Object object = super.read(type, contextClass, inputMessage);
        if (!(object instanceof ReqObjBase))
            log.warn("read data type is not instanceof {}", ReqObjBase.class.getName());
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        log.info("read request body data: {}", objectMapper.writeValueAsString(object));
        return object;
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!(object instanceof JsonResult))
            log.warn("return data type is not instanceof {}, which is {}", JsonResult.class.getName(), object.getClass().getName());
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        String jsonStr = objectMapper.writeValueAsString(object);
        log.info("write response body data: {}", jsonStr);
        if (SystemUtil.isMaskResp()) {
            object = objectMapper.readValue(jsonStr, Object.class);
        }
        super.writeInternal(object, type, outputMessage);
    }
}
