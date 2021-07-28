package com.itsu.site.framework.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsu.core.entity.Account;
import com.itsu.core.shiro.AesUsernamePasswordToken;
import com.itsu.core.shiro.AuthenRealmBase;
import com.itsu.core.util.ByteSourceUtil;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import com.itsu.site.framework.mapper.AccountMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @ClassName: LoginRealm.java
 * @Description: 登录的shiro realm
 * @author Jerry Su
 * @Date 2020年12月23日 上午9:01:57
 */
public class AuthenRealm extends AuthenRealmBase {

	@Autowired
	private AccountMapper accountMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(getRoles(username));
		info.setStringPermissions(getStringPermissions(username));
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		Account user = getUserInfoByUserName(username);
		if (user != null) {
			return new SimpleAuthenticationInfo(username, user.getPassword(), ByteSourceUtil.bytes(user.getSalt()),
					getName());
		}
		return null;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		if (SystemUtil.isLoginAesEncrypt()) {
			return token instanceof AesUsernamePasswordToken;
		} else
			return token instanceof UsernamePasswordToken;
	}

	@Override
	protected Set<String> getStringPermissions(String username) {
		return accountMapper.getPermissionsByUsername(username);
	}

	@Override
	protected Set<String> getRoles(String username) {
		return accountMapper.getRolesByUsername(username);
	}

	@Override
	protected Account getUserInfoByUserName(String username) {
		QueryWrapper<Account> condition = new QueryWrapper<>();
		condition.eq(ItsuSiteConstant.USER_NAME, username);
		return accountMapper.selectOne(condition);
	}


}
