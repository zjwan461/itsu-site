package com.itsu.site.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsu.core.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jerry.Su
 * @Date 2021/7/21 16:20
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
