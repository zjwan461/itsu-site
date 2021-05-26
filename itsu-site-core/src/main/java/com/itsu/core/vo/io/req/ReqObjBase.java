package com.itsu.core.vo.io.req;

import com.itsu.core.entity.EntityBase;
import com.itsu.core.vo.io.ReqRespBase;

public interface ReqObjBase<T extends EntityBase> extends ReqRespBase {

    T transform2Entity();
}
