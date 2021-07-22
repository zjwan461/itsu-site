package com.itsu.site.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsu.core.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 15:47
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    void deleteRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void addRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<Role> getRolePermissions(@Param("roleId") Long roleId);
}
