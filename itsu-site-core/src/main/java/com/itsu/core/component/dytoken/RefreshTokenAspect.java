/*
 * @Author: Jerry Su
 * @Date: 2021-01-27 11:09:37
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-07 14:50:45
 */
package com.itsu.core.component.dytoken;

import cn.hutool.extra.servlet.ServletUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.exception.DynamicTokenException;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.util.RequestContextHolderUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import com.itsu.core.vo.sys.RefreshTokenType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Order(2)
public abstract class RefreshTokenAspect {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenAspect.class);

    @Resource
    private ItsuSiteConfigProperties kProperties;

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private LocalTokenBlackList localTokenBlackList;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(com.itsu.core.component.dytoken.RefreshToken)")
    public void rule() {
    }

    /**
     * 验证token是否重复消费&重新颁发新token
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "rule()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        SkipRefreshToken[] srts = ms.getMethod().getAnnotationsByType(SkipRefreshToken.class);
        SkipRefreshToken srt = (srts != null && srts.length > 0) ? srts[0] : null;
        if (srt != null) {
            return joinPoint.proceed();
        }
        RefreshToken[] rts = ms.getMethod().getAnnotationsByType(RefreshToken.class);
        RefreshToken refreshToken = (rts != null && rts.length > 0) ? rts[0] : null;
        HttpServletRequest request = RequestContextHolderUtil.getRequest();
        String oldToken = ServletUtil.getHeader(request, ItsuSiteConstant.ACCESS_TOKEN, ItsuSiteConstant.SYSTEM_ENCODING);
        boolean logioTag = !isLoginLogoutRequest(request);

        boolean refreshTokenFlag = true;
        synchronized (this) {
            try {
                if (RefreshTokenType.REDIS.getValue().equals(SystemUtil.getRefreshTokenType())) {
                    if (logioTag && redisTemplate.opsForValue()
                            .get(kProperties.getAccessToken().getKeyPrefix() + oldToken) != null) {
                        throw new DynamicTokenException();
                    }
                } else if (RefreshTokenType.MEMORY.getValue().equals(SystemUtil.getRefreshTokenType())) {
                    if (logioTag && localTokenBlackList.getToken(oldToken) != null) {
                        throw new DynamicTokenException();
                    }
                } else
                    throw new CodeAbleException(10001,
                            "not valid refresh token type [" + SystemUtil.getRefreshTokenType() + "]");

            } catch (Exception e) {
                refreshTokenFlag = false;
                if (refreshToken != null && refreshToken.exceptionHandler()) {
                    String reasons = refreshToken.reason();
                    logger.debug("Do not need to throw out RefreshToken exception. Here are the reasons [ {} ]",
                            reasons);
                } else {
                    throw e;
                }
            }
            Object result = joinPoint.proceed();
            Subject subject = SecurityUtils.getSubject();

            if (subject.isAuthenticated() && result instanceof JsonResult && logioTag && refreshTokenFlag) {

                String username = JWTUtil.getUsername(oldToken);
                List<String> accesstokens = this.newSign(username);
                JsonResult jr = (JsonResult) result;
                jr.setRefreshTokens(accesstokens);
                logger.debug("success refresh accesstoken [{}] ", accesstokens);
                declineOldToken(oldToken, username);
            } else {
                logger.debug("do not need refresh accesstoken");
            }
            return result;
        }

    }

    /**
     * 重新颁发token
     *
     * @param username
     * @return
     */
    protected abstract List<String> newSign(String username);

    /**
     * 旧token加入redis/memory黑名单
     *
     * @param token
     * @throws CodeAbleException
     */
    protected void declineOldToken(String token, String username) throws CodeAbleException {
        if (RefreshTokenType.REDIS.getValue().equals(SystemUtil.getRefreshTokenType())) {
            redisTemplate.opsForValue().set(kProperties.getAccessToken().getKeyPrefix() + token, username,
                    JWTUtil.getExpireTime(token).getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
        } else if (RefreshTokenType.MEMORY.getValue().equals(SystemUtil.getRefreshTokenType())) {
            localTokenBlackList.pushToken(token, username);
        } else
            throw new CodeAbleException(10001, "not valid refresh token type [" + SystemUtil.getRefreshTokenType() + "]");
    }

    /**
     * 判断是否为登录&登出url
     *
     * @param request
     * @return
     */
    protected boolean isLoginLogoutRequest(HttpServletRequest request) {
        return SystemUtil.isLoginLogoutRequest(request);
    }
}
