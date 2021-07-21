package com.itsu.site.framework.service;

import cn.hutool.core.util.RandomUtil;
import com.itsu.core.api.ARPService;
import com.itsu.core.entity.Account;
import com.itsu.core.entity.Role;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.ARPReqVo;
import com.itsu.core.vo.io.resp.ARPRespVo;
import com.itsu.site.framework.mapper.AccountMapper;
import com.itsu.site.framework.mapper.PermissionMapper;
import com.itsu.site.framework.mapper.RoleMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public JsonResult<ARPRespVo> addAccount(ARPReqVo arpReqVo) throws Exception {
        Account account = new Account();
        String salt = RandomUtil.randomString(32);
        account.setName(arpReqVo.getName());
        account.setSalt(salt);
        account.setPassword(new SimpleHash(SystemUtil.getHashName(), "password", salt, SystemUtil.getHashIterations()).toString());
        account.setUsername(arpReqVo.getUsername());
        accountMapper.insert(account);
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> addRole(ARPReqVo arpReqVo) {
        Role role = new Role();
        role.setName(arpReqVo.getName());
        roleMapper.insert(role);
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> addAccountRole(ARPReqVo arpReqVo) {
        accountMapper.addAccountRole(arpReqVo.getAccountId(), arpReqVo.getRoleId());
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> addRolePermission(ARPReqVo arpReqVo) {
        roleMapper.addRolePermission(arpReqVo.getRoleId(), arpReqVo.getPermissionId());
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> deleteAccount(Long accountId) {
        accountMapper.deleteById(accountId);
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> deleteRole(Long roleId) {
        roleMapper.deleteById(roleId);
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> deletePermission(Long permissionId) {
        permissionMapper.deleteById(permissionId);
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> deleteAccountRole(ARPReqVo arpReqVo) {
        accountMapper.deleteAccountRole(arpReqVo.getAccountId(), arpReqVo.getRoleId());
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> deleteRolePermission(ARPReqVo arpReqVo) {
        roleMapper.deleteRolePermission(arpReqVo.getRoleId(), arpReqVo.getPermissionId());
        return JsonResult.ok();
    }

    @Override
    public JsonResult<ARPRespVo> listRolePermissionTree() {
        return null;
    }
}
