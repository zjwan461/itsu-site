/*
 * @Author: Jerry Su
 * @Date: 2021-02-07 10:12:12
 * @Last Modified by:   Jerry Su
 * @Last Modified time: 2021-02-07 10:12:12
 */
package com.itsu.core.component.mvc;

import cn.hutool.json.JSONUtil;
import com.itsu.core.util.MaskUtil;
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
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class MaskJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(MaskJackson2HttpMessageConverter.class);

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof JsonResult) {
            JsonResult jsonResult = (JsonResult) object;
            Object data = jsonResult.getData();
            Map map = new HashMap<>();
            map.put("code", jsonResult.getCode());
            map.put("msg", jsonResult.getMsg());
            try {
                if (SystemUtil.isMaskLog()) {
                    if (SystemUtil.isSimpleObject(data)) {
                        Mask annotation = data.getClass().getAnnotation(Mask.class);
                        if (annotation != null && annotation.logEnable()) {
                            map.put("data", annotation.value());
                        } else {
                            map.put("data", JSONUtil.toJsonStr(data));
                        }
                        log.info("write response body data: {}", JSONUtil.toJsonStr(map));
                    } else {
                        map.put("data", MaskUtil.doLogMask(data));
                        log.info("write response body data: {}", JSONUtil.toJsonStr(map));
                    }
                } else {
                    log.info("write response body data: {}", JSONUtil.toJsonStr(jsonResult));
                }
                if (SystemUtil.isMaskResp()) {
                    if (SystemUtil.isSimpleObject(object)) {
                        Mask annotation = object.getClass().getAnnotation(Mask.class);
                        if (annotation != null && annotation.respEnable()) {
                            map.put("data", annotation.value());
                        } else {
                            map.put("data", JSONUtil.toJsonStr(object));
                        }
                    } else {
                        map.put("data", MaskUtil.doRespMask(data));
                    }
                    object = map;
                }
            } catch (Exception e) {
                log.warn("error to execute doLogMask/doRespMask, error message: {}", e.getMessage());
            }
        } else {
            log.warn("return data type is not instanceof {}, which is {}", JsonResult.class.getName(), object.getClass().getName());
            log.info("write response body data: {}", JSONUtil.toJsonStr(object));
        }
        super.writeInternal(object, type, outputMessage);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        Object object = super.read(type, contextClass, inputMessage);
        Map map;
        if (object instanceof ReqObjBase) {
            if (SystemUtil.isMaskLog()) {
                try {
                    map = MaskUtil.doLogMask(object);
                    log.info("read request body data: {}", JSONUtil.toJsonStr(map));
                } catch (Exception e) {
                    log.warn("error to execute doLogMask/doRespMask, error message: {}", e.getMessage());
                }
            } else {
                log.info("read request body data: {}", JSONUtil.toJsonStr(object));
            }
        } else {
            log.warn("read data type is not instanceof {}", ReqObjBase.class.getName());
            if (SystemUtil.isSimpleObject(object)) {
                Mask annotation = object.getClass().getAnnotation(Mask.class);
                if (annotation != null && annotation.logEnable()) {
                    log.info("read request body data: {}", annotation.value());
                } else {
                    log.info("read request body data: {}", JSONUtil.toJsonStr(object));
                }
            } else {
                try {
                    map = MaskUtil.doLogMask(object);
                    log.info("read request body data: {}", JSONUtil.toJsonStr(map));
                } catch (Exception e) {
                    log.warn("error to execute doLogMask/doRespMask, error message: {}", e.getMessage());
                }
            }
        }
        return object;
    }

}