package com.itsu.core.api;

import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.ARPReqVo;
import com.itsu.core.vo.io.resp.ARPRespVo;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 13:55
 */
public interface ARPService {

    JsonResult<ARPRespVo> addAccount(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> addRole(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> addPermission(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> addAccountRole(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> addRolePermission(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> deleteAccount(ARPReqVo accountId);

    JsonResult<ARPRespVo> deleteRole(ARPReqVo roleId);

    JsonResult<ARPRespVo> deletePermission(ARPReqVo permissionId);

    JsonResult<ARPRespVo> deleteAccountRole(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> deleteRolePermission(ARPReqVo arpReqVo);

    JsonResult<ARPRespVo> listRolePermissionTree(Long roleId);

    JsonResult<ARPRespVo> listAccountRoleTree(Long accountId);
}
