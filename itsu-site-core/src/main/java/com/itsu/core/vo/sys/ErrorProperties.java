package com.itsu.core.vo.sys;

import java.util.Properties;

public class ErrorProperties extends Properties {

    /**
     * εΊεε
     */
    private static final long serialVersionUID = 3229528974901242712L;

    public String getErrorMsg(int errorCode) {
        return (String) super.getProperty(String.valueOf(errorCode));
    }

    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
