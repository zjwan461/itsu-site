package com.itsu.sample.mapper;

import com.itsu.core.component.cache.MapperCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = MapperCache.class)
public interface TestMapper {

    @Select("select version() version")
    String selectDBVersion();
}
