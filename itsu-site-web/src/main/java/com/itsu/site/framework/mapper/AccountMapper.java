package com.itsu.site.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsu.core.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author Jerry Su
 * @Date 2020年12月23日 上午10:39:31
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    Set<String> getRolesByUsername(String username);

    Set<String> getPermissionsByUsername(String username);

    void deleteAccountRole(@Param("accountId") Long accountId, @Param("roleId") Long roleId);

    void addAccountRole(@Param("accountId") Long accountId, @Param("roleId") Long roleId);
}
