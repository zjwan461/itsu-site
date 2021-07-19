package com.itsu.core.vo.sys;

/**
 * @author Jerry Su
 * @Description: 系统常量枚举类
 * @Date 2020年12月22日 下午8:40:08
 */
public enum CodeConstant {

    DEFAULT_ERROR_CODE(10001),
    AUTHEN_ERROR_CODE(10002),
    AUTHOR_ERROR_CODE(10003),
    BIND_ERROR_CODE(10004),
    AES_ERROR_CODE(10005);

    private Integer errorCode;

    private CodeConstant(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
