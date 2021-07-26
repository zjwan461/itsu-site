package com.itsu.core.api;

import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.LoginReqVo;
import com.itsu.core.vo.io.resp.LoginRespVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jerry Su
 * @Date 2020年12月22日 下午7:31:22
 */
public interface AccountService {

    JsonResult<LoginRespVo> login(LoginReqVo loginReqVo) throws CodeAbleException;

    JsonResult logout(HttpServletRequest request, HttpServletResponse response) throws CodeAbleException;

}
