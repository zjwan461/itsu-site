package com.itsu.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 14:40
 */
@TableName("td_permission")
public class Permission implements EntityBase {

    @TableId(type = IdType.ASSIGN_ID)
    private Long permissionId;

    @TableField("name")
    private String name;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object invoke() {
        return null;
    }
}
