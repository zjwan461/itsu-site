package com.itsu.core.exception;

import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.vo.sys.ErrorProperties;

/**
 * @author Jerry Su
 * @Date 2020年12月22日 下午7:41:15
 */
public class CodeAbleException extends RuntimeException {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 7635197484052054300L;

    private final Integer code;

    private final String message;

    private static final ErrorProperties prop = ErrorPropertiesFactory.getObject();

    public CodeAbleException(Integer code) {
        this(code, prop.getErrorMsg(code));

    }

    public CodeAbleException(Integer code, String message) {
        this(code, message, null);
    }

    public CodeAbleException(Integer code, Throwable cause) {
        this(code, prop.getErrorMsg(code), cause);
    }

    public CodeAbleException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
