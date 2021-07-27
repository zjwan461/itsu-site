/*
 * @Author: Jerry Su
 * @Date: 2020-12-25 08:56:54
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-07 14:44:36
 */
package com.itsu.site.framework.controller.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.itsu.core.component.dytoken.RefreshToken;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.exception.DynamicTokenException;
import com.itsu.core.exception.SingleLoginException;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.sys.CodeConstant;
import com.itsu.core.vo.sys.ErrorProperties;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiExceptionHandler implements ApiExceptionHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Resource
    private ErrorProperties prop;

    /**
     * 自定义 动态token异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = DynamicTokenException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult handleDyException(HttpServletRequest request, DynamicTokenException e) {
        String requestURI = request.getRequestURI();
        logger.error("found Dynamic token bad request for {}, which msg is {}", requestURI, e.getMessage());
        return JsonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 自定义异常拦截方法
     *
     * @param request
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = CodeAbleException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public JsonResult handleCodeAbleException(HttpServletRequest request, CodeAbleException e) {
        String requestURI = request.getRequestURI();
        logger.info("found api bad request for {}, which msg is {}", requestURI, e.getMessage());
        return JsonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 自定义shiro认证异常拦截方法
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public JsonResult handleAuthenticationException(HttpServletRequest request, AuthenticationException e) {
        e.printStackTrace();
        String requestURI = request.getRequestURI();
        logger.info("found authentication fail to request for {}, which msg is {}", requestURI, e.getMessage());
        return JsonResult.error(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode()));
    }

    /**
     * 自定义shiro授权异常拦截方法
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @RefreshToken(exceptionHandler = true)
    public JsonResult handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
        e.printStackTrace();
        String requestURI = request.getRequestURI();
        logger.info("found unauthnorized request for {}, which msg is {} ", requestURI, e.getMessage());
        return JsonResult.error(CodeConstant.AUTHOR_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.AUTHOR_ERROR_CODE.getErrorCode()));
    }

    /**
     * 超过最大请求限制异常处理方法
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public JsonResult handleUploadSizeException(HttpServletRequest request, HttpServletResponse response,
                                                MaxUploadSizeExceededException e) {
        String requestURI = request.getRequestURI();
        logger.info("found MaxUploadSizeExceededException to request for {}, which msg is {}", requestURI,
                e.getMessage());
        return JsonResult.error(CodeConstant.UPLOAD_MAX_ERROR_CODE.getErrorCode(), prop.getErrorMsg(CodeConstant.UPLOAD_MAX_ERROR_CODE.getErrorCode()));
    }

    /**
     * 自定义未知异常拦截方法
     *
     * @param request
     * @param response
     * @param e
     * @return
     * @throws Throwable
     */
    @ExceptionHandler(value = Throwable.class)
    public JsonResult handleException(HttpServletRequest request, HttpServletResponse response, Throwable e)
            throws Throwable {
        e = SystemUtil.getDeepCause(e);
        if (e instanceof CodeAbleException) {
            CodeAbleException ke = (CodeAbleException) e;
            return handleCodeAbleException(request, ke);
        } else if (e instanceof AuthenticationException) {
            AuthenticationException ae = (AuthenticationException) e;
            return handleAuthenticationException(request, ae);
        } else if (e instanceof AuthorizationException) {
            AuthorizationException aoe = (AuthorizationException) e;
            return handleAuthorizationException(request, aoe);
        } else {
            logger.error("unknown exception happen", e);
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), prop.getErrorMsg(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode()));
        }
    }

    /**
     * 自定义数据绑定错误
     *
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult handleBindException() {
        return JsonResult.error(CodeConstant.BIND_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.BIND_ERROR_CODE.getErrorCode()));
    }

    /**
     * 自定义404异常拦截方法
     *
     * @param request
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public JsonResult handle404Exception(HttpServletRequest request, NoHandlerFoundException e) {
        return JsonResult.error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    /**
     * 不满足接口接收参数
     *
     * @param request
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult handleMethodArgumentNotValidException(HttpServletRequest request,
                                                            MethodArgumentNotValidException e) {
        String requestURI = request.getRequestURI();
        logger.info("found request method argument not valid exception request for {}, which msg is {} ", requestURI,
                e.getMessage());
        return JsonResult.error(CodeConstant.BIND_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.BIND_ERROR_CODE.getErrorCode()));
    }

    /**
     * 错误的http请求方法异常
     *
     * @param request
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public JsonResult handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                   HttpRequestMethodNotSupportedException e) {
        String requestURI = request.getRequestURI();
        logger.info("found http request method not support exception request for {}, which msg is {} ", requestURI,
                e.getMessage());
        return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    /**
     * 错误的http请求格式
     *
     * @param request
     * @param e
     * @return
     */
    @RefreshToken(exceptionHandler = true)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult handleHttpMessageNotReadableException(HttpServletRequest request,
                                                            HttpMessageNotReadableException e) {
        String requestURI = request.getRequestURI();
        logger.info("found http message not readable exception request for {}, which msg is {} ", requestURI,
                e.getMessage());
        return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * jwt异常处理,同token验证不通过
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = JWTVerificationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public JsonResult handleJwtException(HttpServletRequest request, JWTVerificationException e) {
        String requestURI = request.getRequestURI();
        logger.info("found request JWT verifi exception request for {}, which msg is {} ", requestURI, e.getMessage());
        return JsonResult.error(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode()));
    }

    /**
     * 唯一登录异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = SingleLoginException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public JsonResult handleSingleLoginException(HttpServletRequest request, SingleLoginException e) {
        String requestURI = request.getRequestURI();
        logger.info("found api bad request for {}, which msg is {}", requestURI, e.getMessage());
        return JsonResult.error(CodeConstant.SINGLE_LOGIN_ERROR_CODE.getErrorCode(),
                prop.getErrorMsg(CodeConstant.SINGLE_LOGIN_ERROR_CODE.getErrorCode()));
    }
}
