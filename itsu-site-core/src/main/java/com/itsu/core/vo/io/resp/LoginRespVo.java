package com.itsu.core.vo.io.resp;

import java.io.Serializable;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:54
 */
public class LoginRespVo implements RespObjBase, Serializable {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
