package com.itsu.core.shiro;

import com.itsu.core.entity.Account;
import org.apache.shiro.realm.AuthorizingRealm;

import java.util.Set;

public abstract class AuthenRealmBase extends AuthorizingRealm {

    /**
     * @param username
     * @return
     * @author Jerry Su
     */
    protected abstract Set<String> getStringPermissions(String username);

    /**
     * @param username
     * @return
     * @author Jerry Su
     */
    protected abstract Set<String> getRoles(String username);

    /**
     * @param username
     * @author Jerry Su
     * @Description: 根据username获取数据库中的 user info
     */
    protected abstract Account getUserInfoByUserName(String username);

}
