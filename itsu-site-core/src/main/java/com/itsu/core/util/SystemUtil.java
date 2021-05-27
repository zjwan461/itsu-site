/*
 * @Author: Jerry Su
 * @Date: 2021-02-01 16:51:51
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-01 16:54:48
 */
package com.itsu.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.vo.io.req.ReqObjBase;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SystemUtil {

    private SystemUtil() {
    }

    /**
     * 获取ioc中的系统配置文件
     *
     * @return
     */
    public static ItsuSiteConfigProperties getItsuSiteConfigProperties() {
        return SpringUtil.getBean(ItsuSiteConfigProperties.class);
    }

    /**
     * 登录是否开启aes加密
     *
     * @return
     */
    public static boolean isLoginAesEncrypt() {
        return getItsuSiteConfigProperties().isLoginAesEncrypt();
    }

    /**
     * 是否开启动态token，token单次有效
     *
     * @return
     */
    public static boolean isAccessTokenDynamic() {
        return getItsuSiteConfigProperties().getAccessToken().isDynamic();
    }

    /**
     * 是否开启日志遮罩
     *
     * @return
     */
    public static boolean isMaskLog() {
        return getItsuSiteConfigProperties().getMask().isLog();
    }

    /**
     * 是否开启json序列化返回遮罩
     *
     * @return
     */
    public static boolean isMaskResp() {
        return getItsuSiteConfigProperties().getMask().isResp();
    }

    /**
     * 获取当前系统名称
     *
     * @return
     */
    public static String getProjectName() {
        return getItsuSiteConfigProperties().getName();
    }

    /**
     * 请求对象全局校验
     *
     * @param regEx
     * @param reqObj
     * @return
     * @throws Exception
     */
    public static boolean checkReqObj(String regEx, Object reqObj) throws Exception {
        if (!(reqObj instanceof ReqObjBase)) {
            return false;
        }
        boolean result = false;
        List<Field> fields = getAllSiteFields(reqObj, new ArrayList<>());
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if (hasChildNode(field) && field.get(reqObj) != null) {
                return checkReqObj(regEx, field.get(reqObj));
            } else {
                Object object = field.get(reqObj);
                if (object instanceof String) {
                    String str = (String) object;
                    if (StrUtil.isNotBlank(str)) {
                        result = str.matches(regEx);
                    }
                }
                if (result)
                    break;
            }
        }

        return result;
    }

    public static List<Field> getAllSiteFields(Object reqObj, List<Field> fields) throws Exception {
        if (!(reqObj instanceof ReqObjBase)) {
            return fields;
        } else {
            Field[] declaredFields = reqObj.getClass().getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            return getAllSiteFields(reqObj.getClass().getSuperclass().newInstance(), fields);
        }

    }

    private static boolean hasChildNode(Field field) {
        return !String.class.isAssignableFrom(field.getType()) && !Integer.class.isAssignableFrom(field.getType())
                && !Double.class.isAssignableFrom(field.getType()) && !Float.class.isAssignableFrom(field.getType())
                && !Boolean.class.isAssignableFrom(field.getType()) && boolean.class != field.getType()
                && char.class != field.getType() && void.class != field.getType();
    }

    /**
     * 判断是否为登录&登出url
     *
     * @param request
     * @return
     */
    public static boolean isLoginLogoutRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getServletContext().getContextPath();
        if ((contextPath + getItsuSiteConfigProperties().getSecurityConfig().getLoginUrl()).equals(requestURI)
                || (contextPath + getItsuSiteConfigProperties().getSecurityConfig().getLogoutUrl()).equals(requestURI)) {
            return true;
        }
        return false;
    }

    /**
     * 获取动态token机制是redis还是memory
     *
     * @return
     */
    public static String getRefreshTokenType() {
        return getItsuSiteConfigProperties().getAccessToken().getType().getValue();
    }

    /**
     * 获取最深层包装的异常
     *
     * @param throwable
     * @return
     */
    public static Throwable getDeepCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable;
        } else {
            return getDeepCause(cause);
        }
    }

    /**
     * 获取当前运行环境 dev/sit/uat/prd
     *
     * @return
     */
    public static String getEnv() {
        return getItsuSiteConfigProperties().getEnv();
    }

    /**
     * 判断是否为生产环境
     *
     * @return
     */
    public static boolean isPrd() {
        return "prd".equalsIgnoreCase(getEnv());
    }


    /**
     * 获取自定义ErrorProperties位置，需要保证在类路径下
     *
     * @return
     */
    public static String getCustomErrorPropertiesPath() {
        return getItsuSiteConfigProperties().getCustomErrorProperties();
    }

}
