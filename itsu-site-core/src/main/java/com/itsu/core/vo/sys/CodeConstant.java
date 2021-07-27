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
    AES_ERROR_CODE(10005),
    SINGLE_LOGIN_ERROR_CODE(10006),
    TOKEN_REPEAT_ERROR_CODE(10007),
    REQUEST_PARAM_ERROR_CODE(10008),
    UPLOAD_MAX_ERROR_CODE(10009),
    ;

    private final Integer errorCode;

    CodeConstant(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
