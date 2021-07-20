package com.itsu.sample.mapper;

import com.itsu.core.component.cache.MapperCache;
import com.itsu.core.entity.Account;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(implementation = MapperCache.class)
public interface TestMapper {

    @Select("select version() version")
    String selectDBVersion();

    @Select("select * from td_account")
    List<Account> selectAccounts();
}
