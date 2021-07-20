package com.itsu.core.util;

import cn.hutool.json.JSONUtil;
import com.itsu.core.component.mvc.Mask;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static Map doLogMask(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        List<Field> declaredFields = SystemUtil.getAllSiteFields(data, new ArrayList<>());
//        Field[] declaredFields = data.getClass().getDeclaredFields();
        Map map = new HashMap<>();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if (SystemUtil.isNotSimpleField(field) && field.get(data) != null) {
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
    public static Map doRespMask(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        List<Field> declaredFields = SystemUtil.getAllSiteFields(data, new ArrayList<>());
//        Field[] declaredFields = data.getClass().getDeclaredFields();
        Map map = new HashMap<>();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
//            if (isNotSimpleField(field) && field.get(data) != null) {
            if (SystemUtil.isNotSimpleField(field) && field.get(data) != null) {
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

    private static final List<String> MASK_STR_LIST = new ArrayList<>();

    static {
        MASK_STR_LIST.add("password");
        MASK_STR_LIST.add("bank");
    }

    public static String maskJsonStr(Object object) {
        String jsonStr = JSONUtil.toJsonStr(object);
        return jsonStr;
    }
}
