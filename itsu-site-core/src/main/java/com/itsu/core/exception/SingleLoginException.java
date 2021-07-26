package com.itsu.core.exception;

/**
 * @author Jerry.Su
 * @Date 2021/7/26 10:27
 */
public class SingleLoginException extends CodeAbleException {
    public SingleLoginException() {
        super(10006);
    }

    public SingleLoginException(String message) {
        super(10006, message);
    }
}
