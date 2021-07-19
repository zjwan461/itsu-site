package com.itsu.core.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jerry Su
 * @ClassName: JsonResult.java
 * @Description: 返回前端的json实体对象
 * @Date 2020年12月22日 下午12:14:43
 */
@JsonInclude(value = Include.NON_NULL)
public class JsonResult<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8904169600859563387L;
    private Integer code;
    private String msg;
    private T data;
    //	private String refreshToken;
    private List<String> refreshTokens;

    public JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this(code, msg, null);
    }

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(00000, "OK", data);
    }

    public static <T> JsonResult<T> error() {
        return error(10001);
    }

    public static <T> JsonResult<T> error(Integer code) {
        return error(code, "error");
    }

    public static <T> JsonResult<T> error(Integer code, String msg) {
        return new JsonResult<>(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public JsonResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public JsonResult<T> data(T data) {
        this.data = data;
        return this;
    }

//	/**
//	 * @return the refreshToken
//	 */
//	public String getRefreshToken() {
//		return refreshToken;
//	}
//
//	/**
//	 * @param refreshToken the refreshToken to set
//	 */
//	public void setRefreshToken(String refreshToken) {
//		this.refreshToken = refreshToken;
//	}

    public List<String> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<String> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }
}