package com.itsu.sample.mapper;

import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Select("select version() version")
    String selectDBVersion();
}
