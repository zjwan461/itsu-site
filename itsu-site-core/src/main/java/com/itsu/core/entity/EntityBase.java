/*
 * @Author: Jerry Su 
 * @Date: 2021-01-05 08:46:45 
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-01-05 08:53:49
 */
package com.itsu.core.entity;

import cn.hutool.core.bean.BeanUtil;
import com.itsu.core.vo.io.TransformSetting;
import com.itsu.core.vo.io.resp.RespObjBase;

public interface EntityBase<T extends RespObjBase> extends TransformSetting<T> {

    default T transform2RespObject(){
        T invoke = invoke();
        BeanUtil.copyProperties(this, invoke, options());
        return invoke;
    }
}
