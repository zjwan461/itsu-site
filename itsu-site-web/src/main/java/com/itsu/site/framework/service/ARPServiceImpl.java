package com.itsu.site.framework.service;

import cn.hutool.core.util.RandomUtil;
import com.itsu.core.api.ARPService;
import com.itsu.core.entity.Account;
import com.itsu.core.entity.Permission;
import com.itsu.core.entity.Role;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.ARPReqVo;
import com.itsu.core.vo.io.resp.ARPRespVo;
import com.itsu.core.vo.sys.CodeConstant;
import com.itsu.site.framework.mapper.AccountMapper;
import com.itsu.site.framework.mapper.PermissionMapper;
import com.itsu.site.framework.mapper.RoleMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 15:15
 */
@Transactional(rollbackFor = Exception.class)
public class ARPServiceImpl implements ARPService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public JsonResult<ARPRespVo> addAccount(ARPReqVo arpReqVo) {
        Account account = new Account();
        String salt = RandomUtil.randomString(32);
        account.setName(arpReqVo.getName());
        account.setSalt(salt);
        account.setPassword(new SimpleHash(SystemUtil.getHashName(), "password", salt, SystemUtil.getHashIterations()).toString());
        account.setUsername(arpReqVo.getUsername());
        accountMapper.insert(account);
        if (arpReqVo.isCascade()) {
            return this.addAccountRole(arpReqVo);
        }
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> addRole(ARPReqVo arpReqVo) {
        Role role = new Role();
        if (arpReqVo != null && StringUtils.hasText(arpReqVo.getRoleName())) {
            role.setName(arpReqVo.getRoleName());
            roleMapper.insert(role);
            if (arpReqVo.isCascade()) {
                return this.addRolePermission(arpReqVo);
            }
            return JsonResult.ok();
        } else {
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "roleName can not be null or empty");
        }
    }

    @Override
    public JsonResult<ARPRespVo> addPermission(ARPReqVo arpReqVo) {
        Permission permission = new Permission();
        if (arpReqVo != null && StringUtils.hasText(arpReqVo.getPermissionName())) {
            permission.setName(arpReqVo.getPermissionName());
            permissionMapper.insert(permission);
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "PermissionName can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> addAccountRole(ARPReqVo arpReqVo) {
        if (arpReqVo != null && arpReqVo.getAccountId() != null && arpReqVo.getRoleId() != null) {
            accountMapper.addAccountRole(arpReqVo.getAccountId(), arpReqVo.getRoleId());
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "accountId or RoleId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> addRolePermission(ARPReqVo arpReqVo) {
        if (arpReqVo != null && arpReqVo.getRoleId() != null && arpReqVo.getPermissionId() != null) {
            roleMapper.addRolePermission(arpReqVo.getRoleId(), arpReqVo.getPermissionId());
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "RoleId or PermissionId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> deleteAccount(ARPReqVo arpReqVo) {
        if (arpReqVo != null && arpReqVo.getAccountId() != null) {
            accountMapper.deleteById(arpReqVo.getAccountId());
            if (arpReqVo.isCascade()) {
                return this.deleteAccountRole(arpReqVo);
            }
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "accountId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> deleteRole(ARPReqVo arpReqVo) {
        if (arpReqVo != null && arpReqVo.getRoleId() != null) {
            roleMapper.deleteById(arpReqVo.getRoleId());
            if (arpReqVo.isCascade()) {
                JsonResult<ARPRespVo> result = this.deleteAccountRole(arpReqVo);
                if (result.isOK()) {
                    return this.deleteRolePermission(arpReqVo);
                } else
                    return result;
            }
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "roleId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> deletePermission(ARPReqVo arpReqVo) {
        if (arpReqVo != null && arpReqVo.getPermissionId() != null) {
            permissionMapper.deleteById(arpReqVo.getPermissionId());
            if (arpReqVo.isCascade()) {
                return this.deleteRolePermission(arpReqVo);
            }
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "PermissionId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> deleteAccountRole(ARPReqVo arpReqVo) {
        if (arpReqVo != null && (arpReqVo.getAccountId() != null || arpReqVo.getRoleId() != null)) {
            accountMapper.deleteAccountRole(arpReqVo.getAccountId(), arpReqVo.getRoleId());
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "AccountId or RoleId can not be null or empty");
    }

    @Override
    public JsonResult<ARPRespVo> deleteRolePermission(ARPReqVo arpReqVo) {
        if (arpReqVo != null && (arpReqVo.getRoleId() != null || arpReqVo.getPermissionId() != null)) {
            roleMapper.deleteRolePermission(arpReqVo.getRoleId(), arpReqVo.getPermissionId());
            return JsonResult.ok();
        } else
            return JsonResult.error(CodeConstant.DEFAULT_ERROR_CODE.getErrorCode(), "RoleId or PermissionId can not be null or empty");
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public JsonResult<ARPRespVo> listRolePermissionTree(Long roleId) {
        List<Role> rolePermissionsList = roleMapper.getRolePermissions(roleId);
        ARPRespVo arpRespVo = new ARPRespVo();
        arpRespVo.setRolePermissionList(rolePermissionsList);
        return JsonResult.ok(arpRespVo);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public JsonResult<ARPRespVo> listAccountRoleTree(Long accountId) {
        List<Account> accountRolesList = accountMapper.getAccountRolesList(accountId);
        ARPRespVo arpRespVo = new ARPRespVo();
        arpRespVo.setAccountRolesList(accountRolesList);
        return JsonResult.ok(arpRespVo);
    }
}
