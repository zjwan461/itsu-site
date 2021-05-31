package com.itsu.core.vo.io;

import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author Jerry Su
 * @Date 2021/5/31 23:34
 */
public interface TransformSetting<T> {

    T invoke();

    /**
     * 详细参见hutool参考文档, 你还可以设置ignore properties、mapping等配置
     * https://hutool.cn/docs/#/core/JavaBean/Bean%E5%B7%A5%E5%85%B7-BeanUtil
     *
     * @return
     */
    default CopyOptions options() {
        return CopyOptions.create()
                .ignoreCase()
                .ignoreNullValue()
                .ignoreError();

    }
}
