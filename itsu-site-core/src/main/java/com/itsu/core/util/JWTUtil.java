package com.itsu.core.util;

import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: JWTUtil.java
 * @Description: JWT 加密解密工具类
 * @author Jerry Su
 * @Date 2020年12月22日 下午3:45:21
 */
public class JWTUtil {

	private JWTUtil() {
	}

	// 过期时间1小时
	public static final long EXPIRE_TIME = TimeUnit.MINUTES.toMillis(60);
//			1 * 60 * 60 * 1000 * 1L;

	/**
	 * 生成签名(默认过期时间)
	 *
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return 加密的token
	 */
	public static String sign(final String username, final String secret) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(secret)) {
			throw new IllegalArgumentException("username or secret is null or empty");
		}
		return JWT.create().withClaim(ItsuSiteConstant.USER_NAME, username)
				.withClaim("current" + RandomUtil.randomString(2), System.currentTimeMillis())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME)).sign(Algorithm.HMAC256(secret));
	}

	/**
	 * 生成token(自定义过期时间)
	 * 
	 * @param username
	 * @param secret
	 * @param expireTime
	 * @return
	 */
	public static String sign(final String username, final String secret, long expireTime) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(secret)) {
			throw new IllegalArgumentException("username or secret is null or empty");
		}
		return JWT.create().withClaim(ItsuSiteConstant.USER_NAME, username)
				.withClaim("current" + RandomUtil.randomString(2), System.currentTimeMillis())
				.withExpiresAt(new Date(System.currentTimeMillis() + expireTime)).sign(Algorithm.HMAC256(secret));
	}

	/**
	 * 校验token
	 * 
	 * @param token
	 * @param secret
	 * @throws JWTVerificationException
	 */
	public static void verify(final String token, final String secret) throws JWTVerificationException {
		try {
			JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(e.getMessage());
		}
	}

	/**
	 * 解密token，这里取不到secret的
	 * 
	 * @param token
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static DecodedJWT decode(final String token) throws IllegalArgumentException {
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("token is null or empty");
		}
		return JWT.decode(token);
	}

	/**
	 * 获取token过期时间
	 * 
	 * @param token
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Date getExpireTime(final String token) throws IllegalArgumentException {
		final DecodedJWT decodeJwt = decode(token);
		return decodeJwt.getExpiresAt();
	}

	/**
	 * 获取token中的username凭证
	 * 
	 * @param token
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String getUsername(final String token) throws IllegalArgumentException {
		return decode(token).getClaim(ItsuSiteConstant.USER_NAME).asString();
	}
}
