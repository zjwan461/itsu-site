/*
 * @Author: Jerry Su
 * @Date: 2021-01-27 10:35:23
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-27 10:58:34
 */
package com.itsu.core.exception;

import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.vo.sys.CodeConstant;

public class DynamicTokenException extends CodeAbleException {


    /**
     * 序列化
     */
    private static final long serialVersionUID = 7778013024568579884L;

    public DynamicTokenException() {
        super(CodeConstant.TOKEN_REPEAT_ERROR_CODE.getErrorCode(), ErrorPropertiesFactory.getObject().getErrorMsg(CodeConstant.TOKEN_REPEAT_ERROR_CODE.getErrorCode()));
    }

    /**
     * @param message
     */
    public DynamicTokenException(String message) {
        super(CodeConstant.TOKEN_REPEAT_ERROR_CODE.getErrorCode(), message);
    }

}
