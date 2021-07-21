package com.itsu.core.vo.sys;

import org.apache.shiro.crypto.hash.*;

import java.util.LinkedHashMap;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 15:34
 */
public class HashMapping {

    public static LinkedHashMap<String, Class<? extends SimpleHash>> HASH_MAPPING = null;

    static {
        HASH_MAPPING = new LinkedHashMap<>();
        HASH_MAPPING.put("MD5", Md5Hash.class);
        HASH_MAPPING.put("MD2", Md2Hash.class);
        HASH_MAPPING.put("SHA512", Sha512Hash.class);
        HASH_MAPPING.put("SHA384", Sha384Hash.class);
        HASH_MAPPING.put("SHA256", Sha256Hash.class);
        HASH_MAPPING.put("SHA1", Sha1Hash.class);
    }

    public static SimpleHash getHashObject(String hashName) throws Exception {
        return HASH_MAPPING.get(hashName).newInstance();
    }
}
