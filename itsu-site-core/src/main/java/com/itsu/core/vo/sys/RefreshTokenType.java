package com.itsu.core.vo.sys;

public enum RefreshTokenType {
    REDIS("redis"), MEMORY("memory");

    private String value;

    /**
     * @param value
     */
    private RefreshTokenType(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

}
