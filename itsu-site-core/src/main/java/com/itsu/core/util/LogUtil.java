package com.itsu.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jerry Su
 * @Date 2021/7/24 18:51
 */
public class LogUtil {

    private LogUtil() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static void debug(Class<?> clazz, String message, Object... params) {
        getLogger(clazz).debug(message, params);
    }

    public static void debug(String name, String message, Object... params) {
        getLogger(name).debug(message, params);
    }

    public static void info(Class<?> clazz, String message, Object... params) {
        getLogger(clazz).info(message, params);
    }

    public static void info(String name, String message, Object... params) {
        getLogger(name).info(message, params);
    }

    public static void warn(Class<?> clazz, String message, Object... params) {
        getLogger(clazz).warn(message, params);
    }

    public static void warn(String name, String message, Object... params) {
        getLogger(name).warn(message, params);
    }

    public static void error(Class<?> clazz, String message, Object... params) {
        getLogger(clazz).error(message, params);
    }

    public static void error(String name, String message, Object... params) {
        getLogger(name).error(message, params);
    }

}
