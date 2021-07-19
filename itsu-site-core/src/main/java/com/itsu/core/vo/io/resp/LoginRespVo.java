package com.itsu.core.vo.io.resp;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:54
 */
public class LoginRespVo implements RespObjBase, Serializable {

    private String accessToken;

    private List<String> backUpTokens;

    public List<String> getBackUpTokens() {
        return backUpTokens;
    }

    public void setBackUpTokens(List<String> backUpTokens) {
        this.backUpTokens = backUpTokens;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
