package com.itsu.core.vo.sys;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

public class ByteSource implements org.apache.shiro.util.ByteSource, Serializable {

    private static final long serialVersionUID = 1269274896730884458L;
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public ByteSource() {

    }

    public ByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public ByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public ByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public ByteSource(org.apache.shiro.util.ByteSource source) {
        this.bytes = source.getBytes();
    }

    public ByteSource(File file) {
        this.bytes = (new ByteSource.BytesHelper()).getBytes(file);
    }

    public ByteSource(InputStream stream) {
        this.bytes = (new ByteSource.BytesHelper()).getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof org.apache.shiro.util.ByteSource
                || o instanceof File || o instanceof InputStream;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }

        return this.cachedHex;
    }

    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    public String toString() {
        return this.toBase64();
    }

    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof org.apache.shiro.util.ByteSource) {
            org.apache.shiro.util.ByteSource bs = (org.apache.shiro.util.ByteSource) o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
