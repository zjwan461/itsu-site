package com.itsu.core.vo.sys;

import com.baomidou.mybatisplus.annotation.TableField;

public class LoginObject {

    @TableField(exist = false)
    private String securityKey;

    public LoginObject() {
    }

    public LoginObject(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getSecurityKey() {
        return this.securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

}
