package com.itsu.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 14:28
 */
@TableName("td_role")
public class Role implements EntityBase {


    @TableId(type = IdType.ASSIGN_ID)
    private Long roleId;

    @TableField("name")
    private String name;

    @TableField(exist = false)
    private List<Permission> permissions;

    @Override
    public Object invoke() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
