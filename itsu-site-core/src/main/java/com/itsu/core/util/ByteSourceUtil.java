package com.itsu.core.util;

import com.itsu.core.vo.sys.ByteSource;

public class ByteSourceUtil {

    private ByteSourceUtil() {
    }

    public static org.apache.shiro.util.ByteSource bytes(byte[] bytes) {
        return new ByteSource(bytes);
    }

    public static org.apache.shiro.util.ByteSource bytes(String arg0) {
        return new ByteSource(arg0.getBytes());
    }

}
