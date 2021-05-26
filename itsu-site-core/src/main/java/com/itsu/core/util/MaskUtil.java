package com.itsu.core.util;

import com.itsu.core.component.mvc.Mask;
import com.itsu.core.vo.io.ReqRespBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MaskUtil {

    private MaskUtil() {
    }

    /**
     * 日志遮罩替换方法
     * 
     * @param data
     * @return
     * @throws IllegalAccessException
     */
    public static Map doLogMask(Object data) throws IllegalAccessException {
        if (data == null) {
            return null;
        }
        Field[] declaredFields = data.getClass().getDeclaredFields();
        Map map = new HashMap<>();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if (hasChildNode(field) && field.get(data) != null) {
                map.put(field.getName(), doLogMask(field.get(data)));
            } else {
                if (field.isAnnotationPresent(Mask.class)) {
                    Mask mask = field.getAnnotation(Mask.class);
                    if (mask.logEnable()) {
                        map.put(field.getName(), mask.value());
                    } else {
                        map.put(field.getName(), field.get(data));
                    }
                } else {
                    map.put(field.getName(), field.get(data));
                }
            }

        }
        return map;
    }

    /**
     * response遮罩替换方法
     * 
     * @param data
     * @return
     * @throws IllegalAccessException
     */
    public static Map doRespMask(Object data) throws IllegalAccessException {
        if (data == null) {
            return null;
        }
        Field[] declaredFields = data.getClass().getDeclaredFields();
        Map map = new HashMap<>();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if (hasChildNode(field) && field.get(data) != null) {
                map.put(field.getName(), doLogMask(field.get(data)));
            } else {
                if (field.isAnnotationPresent(Mask.class)) {
                    Mask mask = field.getAnnotation(Mask.class);
                    if (mask.respEnable()) {
                        map.put(field.getName(), mask.value());
                    } else {
                        map.put(field.getName(), field.get(data));
                    }
                } else {
                    map.put(field.getName(), field.get(data));
                }
            }

        }
        return map;
    }

    private static boolean hasChildNode(Field field) {
        return ReqRespBase.class.isAssignableFrom(field.getType());
    }
}
