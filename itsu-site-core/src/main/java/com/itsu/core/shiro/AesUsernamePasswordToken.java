/*
 * @Author: Jerry Su
 * @Date: 2020-12-29 11:28:36
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-06 13:52:31
 */
package com.itsu.core.shiro;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.ItsuSiteConfigProperties;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.util.AESUtil;
import com.itsu.core.vo.sys.CodeConstant;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AesUsernamePasswordToken extends UsernamePasswordToken {

    private static final Logger logger = LoggerFactory.getLogger(AesUsernamePasswordToken.class);

    /**
     * 序列化
     */
    private static final long serialVersionUID = -300284742629754529L;

    /**
     * @param username
     * @param password
     */
    public AesUsernamePasswordToken(String username, String password) throws CodeAbleException {
        ItsuSiteConfigProperties kProperties = SpringUtil.getBean(ItsuSiteConfigProperties.class);
        String aesKey = kProperties.getAesKey();
        if (StringUtils.isEmpty(aesKey)) {
            logger.error("AES key can not be empty");
            throw new CodeAbleException(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode());
        }
        try {
            username = AESUtil.decrypt(username, aesKey);
            password = AESUtil.decrypt(password, aesKey);
        } catch (Exception e) {
//            logger.error("AES decode error", e);
            throw new CodeAbleException(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "AES decode error");
        }
        this.setUsername(username);
        this.setPassword(password.toCharArray());
    }

}
