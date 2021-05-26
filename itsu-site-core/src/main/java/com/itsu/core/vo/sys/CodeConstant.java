package com.itsu.core.vo.sys;

/**
 * @author Jerry Su
 * @Description: 系统常量枚举类
 * @Date 2020年12月22日 下午8:40:08
 */
public enum CodeConstant {

    DEFAULT_ERROR_CODE(10001),
    AUTHEN_ERROR_CODE(10002),
    BIND_ERROR_CODE(10003),
    INVALID_USERNAME_OR_PASSWORD(10010);

    private Integer errorCode;

    private CodeConstant(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
