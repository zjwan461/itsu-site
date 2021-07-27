package com.itsu.core.exception;

/**
 * @author Jerry Su
 * @Date 2021/5/31 20:39
 */
public class InitialException extends RuntimeException {

    public InitialException() {
    }

    public InitialException(String message) {
        super(message);
    }

    public InitialException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitialException(Throwable cause) {
        super(cause);
    }
}
