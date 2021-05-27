/*
 * @Author: Jerry Su
 * @Date: 2021-01-27 10:35:23
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-27 10:58:34
 */
package com.itsu.core.exception;

import com.itsu.core.util.ErrorPropertiesFactory;

public class DynamicTokenException extends CodeAbleException {


    /**
     * 序列化
     */
    private static final long serialVersionUID = 7778013024568579884L;

    public DynamicTokenException() {
        super(20001, ErrorPropertiesFactory.getObject().getErrorMsg(20001));
    }

    /**
     * @param message
     */
    public DynamicTokenException(String message) {
        super(20001, message);
    }

}
