package com.itsu.core.vo.io.req;

import com.itsu.core.component.mvc.Mask;
import com.itsu.core.entity.Account;

import javax.validation.constraints.NotBlank;

/**
 * @author Jerry Su
 * @Date 2021/5/21 10:52
 */
public class LoginReqVo implements ReqObjBase<Account> {

    @NotBlank
    private String username;

    @Mask
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Account invoke() {
        return new Account();
    }
}
