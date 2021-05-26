/*
 * @Author: Jerry Su 
 * @Date: 2021-01-27 10:35:23 
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-27 10:58:34
 */
package com.itsu.core.exception;

import com.itsu.core.util.ErrorPropertiesFactory;

public class DynamicTokenException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 7778013024568579884L;

	private final Integer code;

	private final String message;

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	@Override
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param code
	 * @param message
	 */
	public DynamicTokenException() {
		this.code = 20001;
		this.message = ErrorPropertiesFactory.getObject().getErrorMsg(20001);
	}

	/**
	 * @param message
	 * @param code
	 */
	public DynamicTokenException(String message) {
		super(message);
		this.code = 20001;
		this.message = message;
	}

}
