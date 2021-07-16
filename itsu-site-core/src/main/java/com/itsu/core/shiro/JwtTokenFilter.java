package com.itsu.core.shiro;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsu.core.component.cache.ThreadCache;
import com.itsu.core.component.mvc.SpringMvcHelper;
import com.itsu.core.component.security.AccessStrategy;
import com.itsu.core.component.security.StrategyEnum;
import com.itsu.core.entity.Account;
import com.itsu.core.util.ErrorPropertiesFactory;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.sys.CodeConstant;
import com.itsu.core.vo.sys.LoginObject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jerry Su
 * @ClassName: JwtFilter.java
 * @Description: JWT shiro filter
 * @Date 2020年12月23日 上午10:05:06
 */
public abstract class JwtTokenFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
        // 请求结束清空线程变量缓存，避免缓存数据被脏读或内存泄露
        log.debug("start to clean thread cache");
        ThreadCache.removeString();
        ThreadCache.clean();
        log.debug("finish clean thread cache");
    }

    /**
     * 校验token
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        SpringMvcHelper springMvcHelper = SpringUtil.getBean(SpringMvcHelper.class);
        AccessStrategy accessStrategy = (AccessStrategy) springMvcHelper.getHandlerMethodBeanAnnotation(WebUtils.toHttp(request), AccessStrategy.class);
        if (accessStrategy == null) {
            accessStrategy = (AccessStrategy) springMvcHelper.getHandlerMethodAnnotation(WebUtils.toHttp(request), AccessStrategy.class);
        }
        if (accessStrategy != null && accessStrategy.value() == StrategyEnum.ANY)
            return true;

        boolean verify = false;
        String accessToken = ServletUtil.getHeader(WebUtils.toHttp(request), "accessToken", "utf-8");
        if (StringUtils.hasText(accessToken)) {
            String username = null;
            try {
                username = JWTUtil.getUsername(accessToken);
            } catch (Exception e) {
                log.info("bad accessToken for {}; can not decode accessToken: [{}]",
                        WebUtils.toHttp(request).getRequestURI(), accessToken);
            }
            if (StringUtils.hasText(username)) {
                LoginObject user = getAccountInfoByUserName(username);
                if (user != null) {
                    try {
                        JWTUtil.verify(accessToken, user.getSecurityKey());
                        verify = true;
                        // 将用户名存入线程变量中
                        ThreadCache.saveString(username);
                    } catch (Exception e) {
                        log.info("bad accessToken for {} ", WebUtils.toHttp(request).getRequestURI());
                    }
                } else
                    log.info("can not find the account {} ", username);
            }
        } else
            log.info("bad request, no accessToken found ");
        return verify;
    }

    /**
     * @param username
     * @return
     * @author Jerry Su
     */
    protected abstract Account getAccountInfoByUserName(String username);


    /**
     * jwt filter校验不通过后走这个方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = WebUtils.toHttp(response);
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        JsonResult<Object> errorObject = JsonResult.error(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode(),
                ErrorPropertiesFactory.getObject().getErrorMsg(CodeConstant.AUTHEN_ERROR_CODE.getErrorCode()));
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        resp.getWriter().write(objectMapper.writeValueAsString(errorObject));
        resp.getWriter().close();
        return false;
    }

}
