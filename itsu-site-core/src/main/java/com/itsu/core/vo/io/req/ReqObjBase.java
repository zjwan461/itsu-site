package com.itsu.core.vo.io.req;

import cn.hutool.core.bean.BeanUtil;
import com.itsu.core.entity.EntityBase;
import com.itsu.core.vo.io.ReqRespBase;
import com.itsu.core.vo.io.TransformSetting;

public interface ReqObjBase<T extends EntityBase> extends ReqRespBase, TransformSetting<T> {

    default T transform2Entity() {
        T entity = invoke();
        assert entity != null;
        BeanUtil.copyProperties(this, entity, options());
        return entity;
    }

}
