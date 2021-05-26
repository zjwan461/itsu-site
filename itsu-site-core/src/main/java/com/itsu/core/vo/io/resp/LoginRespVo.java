package com.itsu.core.vo.io.resp;

import java.io.Serializable;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:54
 */
public class LoginRespVo implements RespObjBase, Serializable {

    private String accesstoken;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
