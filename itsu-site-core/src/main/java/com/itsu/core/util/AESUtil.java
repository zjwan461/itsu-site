package com.itsu.core.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @ClassName: AESUtil.java
 * @Description: AES工具类
 * @author liqingquan
 * @Date 2021年1月5日 上午9:33:32
 */
public final class AESUtil {

    private AESUtil() {
    }

    /**
     * AES 解密方法
     * 
     * @param str
     * @param aesKey
     * @return
     */
    public static String decrypt(String str, String aesKey) {
        return new SymmetricCrypto(SymmetricAlgorithm.AES, aesKey.getBytes()).decryptStr(str);
    }

    /**
     * AES 加密方法
     * 
     * @param str
     * @param aesKey
     * @return
     */
    public static String encrypt(String str, String aesKey) {
        return new SymmetricCrypto(SymmetricAlgorithm.AES, aesKey.getBytes()).encryptBase64(str);
    }
}
