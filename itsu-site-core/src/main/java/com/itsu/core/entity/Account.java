package com.itsu.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itsu.core.vo.io.resp.LoginRespVo;
import com.itsu.core.vo.sys.LoginObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jerry Su
 * @Date 2020年12月22日 下午4:57:40
 */
@TableName("td_account")
public class Account extends LoginObject implements EntityBase<LoginRespVo>, Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -8560602221587829116L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long accountId;

    @TableField
    private String username;

    @TableField
    private String password;

    @TableField
    private String salt;

    @TableField
    private String name;

    @TableField
    private String lastLoginTime;

    @TableField(exist = false)
    private List<Role> roles;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getSecurityKey() {
        return this.password;
    }

    @Override
    public LoginRespVo invoke() {
        return new LoginRespVo();
    }
}
