package com.itsu.core.vo.sys;

import java.util.Properties;

public class ErrorProperties extends Properties {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 3229528974901242712L;

    public String getErrorMsg(int errorCode) {
        return (String) super.getProperty(String.valueOf(errorCode));
    }

}
