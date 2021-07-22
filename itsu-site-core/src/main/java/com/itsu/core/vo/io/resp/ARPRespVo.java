package com.itsu.core.vo.io.resp;

import com.itsu.core.entity.Account;
import com.itsu.core.entity.Role;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 15:08
 */
public class ARPRespVo implements RespObjBase, Serializable {

    private static final long serialVersionUID = 3215245536219913270L;


    private List<Account> accountRolesList;

    private List<Role> rolePermissionList;

    public List<Role> getRolePermissionList() {
        return rolePermissionList;
    }

    public void setRolePermissionList(List<Role> rolePermissionList) {
        this.rolePermissionList = rolePermissionList;
    }

    public List<Account> getAccountRolesList() {
        return accountRolesList;
    }

    public void setAccountRolesList(List<Account> accountRolesList) {
        this.accountRolesList = accountRolesList;
    }
}
