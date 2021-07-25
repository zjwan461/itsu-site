/*
 * @Author: Jerry Su
 * @Date: 2021-02-01 16:51:51
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-01 16:54:48
 */
package com.itsu.core.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.component.event.LoginEvent;
import com.itsu.core.vo.io.req.ReqObjBase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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
            if (isNotSimpleField(field) && field.get(reqObj) != null) {
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
        try {
            if (Object.class.getName().equals(reqObj.getClass().getName())) {
                return fields;
            } else {
                Field[] declaredFields = reqObj.getClass().getDeclaredFields();
                fields.addAll(Arrays.asList(declaredFields));
                return getAllSiteFields(reqObj.getClass().getSuperclass().newInstance(), fields);
            }
        } catch (Exception e) {
            return fields;
        }

    }

    /**
     * 8种基本数据类型和其包装类以及String以及一些常用集合接口的实现
     *
     * @param field
     * @return
     */
    public static boolean isNotSimpleField(Field field) {
        return !List.class.isAssignableFrom(field.getType()) && !Set.class.isAssignableFrom(field.getType()) && !Map.class.isAssignableFrom(field.getType())
                && !String.class.isAssignableFrom(field.getType()) && !Integer.class.isAssignableFrom(field.getType())
                && !Double.class.isAssignableFrom(field.getType()) && !Float.class.isAssignableFrom(field.getType())
                && !Boolean.class.isAssignableFrom(field.getType()) && !Character.class.isAssignableFrom(field.getType())
                && !Long.class.isAssignableFrom(field.getType()) && !Byte.class.isAssignableFrom(field.getType())
                && !Short.class.isAssignableFrom(field.getType()) && boolean.class != field.getType()
                && char.class != field.getType() && int.class != field.getType()
                && float.class != field.getType() && double.class != field.getType() && long.class != field.getType()
                && byte.class != field.getType() && short.class != field.getType();
    }

    public static boolean isSimpleField(Field field) {
        return !isNotSimpleField(field);
    }

    public static boolean isNotSimpleObject(Object object) {
        return !List.class.isAssignableFrom(object.getClass()) && !Set.class.isAssignableFrom(object.getClass()) && !Map.class.isAssignableFrom(object.getClass())
                && !String.class.isAssignableFrom(object.getClass()) && !Integer.class.isAssignableFrom(object.getClass())
                && !Double.class.isAssignableFrom(object.getClass()) && !Float.class.isAssignableFrom(object.getClass())
                && !Boolean.class.isAssignableFrom(object.getClass()) && !Character.class.isAssignableFrom(object.getClass())
                && !Long.class.isAssignableFrom(object.getClass()) && !Byte.class.isAssignableFrom(object.getClass())
                && !Short.class.isAssignableFrom(object.getClass()) && boolean.class != object.getClass()
                && char.class != object.getClass() && int.class != object.getClass()
                && float.class != object.getClass() && double.class != object.getClass() && long.class != object.getClass()
                && byte.class != object.getClass() && short.class != object.getClass();
    }

    public static boolean isSimpleObject(Object object) {
        return !isNotSimpleObject(object);
    }

    /**
     * 判断是否为登录&登出url,包含logout redirect url
     *
     * @param request
     * @return
     */
    public static boolean isLoginLogoutRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getServletContext().getContextPath();
        if ((contextPath + getItsuSiteConfigProperties().getSecurityConfig().getLoginUrl()).equals(requestURI)
                || (contextPath + getItsuSiteConfigProperties().getSecurityConfig().getLogoutUrl()).equals(requestURI)
                || (contextPath + getItsuSiteConfigProperties().getSecurityConfig().getLogoutRedirect()).equals(requestURI)) {
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

    public static ItsuSiteConfigProperties.MapperCache.CacheType getMapperCacheType() {
        return getItsuSiteConfigProperties().getMapperCache().getCacheType();
    }

    public static int getMapperCacheTime() {
        return getItsuSiteConfigProperties().getMapperCache().getCacheTime();
    }

    public static String getMapperCachePrefix() {
        return getItsuSiteConfigProperties().getMapperCache().getCachePrefix();
    }

    public static Object getStarterClassBean() {
        String[] beanNamesForAnnotation = SpringUtil.getApplicationContext().getBeanNamesForAnnotation(SpringBootApplication.class);
        String beanName = ArrayUtil.isNotEmpty(beanNamesForAnnotation) ? beanNamesForAnnotation[0] : null;
        assert beanName != null;
        return SpringUtil.getBean(beanName);
    }

    public static boolean isMaskJackson() {
        return getItsuSiteConfigProperties().getMask().getType() == ItsuSiteConfigProperties.Mask.Type.JACKSON;
    }

    public static Integer getHashIterations() {
        return getItsuSiteConfigProperties().getSecurityConfig().getHashIterations();
    }

    public static String getHashName() {
        return getItsuSiteConfigProperties().getSecurityConfig().getHashAlgorithmName();
    }

    public static void pushLoginEvent(LoginEvent loginEvent) {
        SpringUtil.getApplicationContext().publishEvent(loginEvent);
    }

    public static boolean isSingleLoginEnable() {
        return getItsuSiteConfigProperties().getSecurityConfig().isSingleLogin();
    }
}
