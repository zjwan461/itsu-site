/*
 * @Author: Jerry Su
 * @Date: 2021-02-07 10:12:16
 * @Last Modified by:   Jerry Su
 * @Last Modified time: 2021-02-07 10:12:16
 */
package com.itsu.core.component.validate;

import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.io.req.ReqObjBase;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@Aspect
@Order(1)
public class GlobalRequestParamValidate {

    private static final Logger log = LoggerFactory.getLogger(GlobalRequestParamValidate.class);

    @Resource
    private ItsuSiteConfigProperties kProperties;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void rule() {
    }

    @Before(value = "rule()")
    public void handle(JoinPoint joinPoint) throws Exception {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        if (!ms.getMethod().isAnnotationPresent(SkipValidate.class)) {
            String[] regExs = kProperties.getGlobalParamCheck().getRegExs();
            for (String regEx : regExs) {
                check(joinPoint, regEx);
            }
        } else
            log.debug("method: {} is marked by @{} , will skip global parameters validate ", ms.getName(),
                    SkipValidate.class.getSimpleName());
    }

    protected void check(JoinPoint joinPoint, String regEx) throws Exception {
        Object[] args = joinPoint.getArgs();
        for (Object object : args) {
            if (object instanceof String) {
                String strObj = (String) object;
                if (strObj.matches(regEx))
                    throw new CodeAbleException(20002);
            } else if (object instanceof ReqObjBase) {
                if (SystemUtil.checkReqObj(regEx, object)) {
                    throw new CodeAbleException(20002);
                }
            } else {
                log.debug("do not need check param");
            }
        }
    }
}
