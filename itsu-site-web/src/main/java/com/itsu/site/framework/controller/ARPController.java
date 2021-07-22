package com.itsu.site.framework.controller;

import com.itsu.core.api.ARPService;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.ARPReqVo;
import com.itsu.core.vo.io.resp.ARPRespVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 11:21
 */
@RequestMapping("/arp")
public class ARPController implements BaseController {

    @Resource
    private ARPService arpService;

    @PostMapping("/addRole")
    public JsonResult<ARPRespVo> addRole(@RequestBody ARPReqVo arpReqVo) {
        return arpService.addRole(arpReqVo);
    }

    @PostMapping("/addAccount")
    public JsonResult<ARPRespVo> addAccount(@RequestBody ARPReqVo arpReqVo) {
        return arpService.addAccount(arpReqVo);
    }

    @PostMapping("/addPermission")
    public JsonResult<ARPRespVo> addPermission(@RequestBody ARPReqVo arpReqVo) {
        return arpService.addPermission(arpReqVo);
    }

    @PostMapping("/addAccountRole")
    public JsonResult<ARPRespVo> addAccountRole(@RequestBody ARPReqVo arpReqVo) {
        return arpService.addAccountRole(arpReqVo);
    }

    @PostMapping("/addRolePermission")
    public JsonResult<ARPRespVo> addRolePermission(@RequestBody ARPReqVo arpReqVo) {
        return arpService.addRolePermission(arpReqVo);
    }

    @DeleteMapping("/deleteAccount")
    public JsonResult<ARPRespVo> deleteAccount(@RequestBody ARPReqVo arpReqVo) {
        return arpService.deleteAccount(arpReqVo);
    }

    @DeleteMapping("/deleteRole")
    public JsonResult<ARPRespVo> deleteRole(@RequestBody ARPReqVo arpReqVo) {
        return arpService.deleteRole(arpReqVo);
    }

    @DeleteMapping("/deletePermission")
    public JsonResult<ARPRespVo> deletePermission(@RequestBody ARPReqVo arpReqVo) {
        return arpService.deletePermission(arpReqVo);
    }

    @DeleteMapping("/deleteAccountRole")
    public JsonResult<ARPRespVo> deleteAccountRole(@RequestBody ARPReqVo arpReqVo) {
        return arpService.deleteAccountRole(arpReqVo);
    }

    @DeleteMapping("/deleteRolePermission")
    public JsonResult<ARPRespVo> deleteRolePermission(@RequestBody ARPReqVo arpReqVo) {
        return arpService.deleteRolePermission(arpReqVo);
    }

    @GetMapping("/listRolePermissionTree/{roleId}")
    public JsonResult<ARPRespVo> listRolePermissionTree(@PathVariable("roleId") Long roleId) {
        return arpService.listRolePermissionTree(roleId);
    }

    @GetMapping("/listAccountRoleTree/{accountId}")
    public JsonResult<ARPRespVo> listAccountRoleTree(@PathVariable("accountId") Long accountId) {
        return arpService.listAccountRoleTree(accountId);
    }
}
