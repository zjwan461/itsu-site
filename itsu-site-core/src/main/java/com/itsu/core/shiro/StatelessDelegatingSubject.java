package com.itsu.core.shiro;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.cache.ThreadCache;
import com.itsu.core.util.JWTUtil;
import com.itsu.core.vo.sys.ItsuSiteConstant;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class StatelessDelegatingSubject extends WebDelegatingSubject {

    private static final Logger logger = LoggerFactory.getLogger(StatelessDelegatingSubject.class);

    public StatelessDelegatingSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionEnabled, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }

    @Override
    public PrincipalCollection getPrincipals() {
        PrincipalCollection principalCollection = null;
        //从线程变量缓存中读取，减少decode jwt的数量，提升性能
        String username = ThreadCache.getString();
        if (!StringUtils.hasText(username)) {
            HttpServletRequest request = WebUtils.toHttp(this.getServletRequest());
            String accessToken = request.getHeader(ItsuSiteConstant.ACCESS_TOKEN);
            if (!StringUtils.hasText(accessToken)) {
                return null;
            }
            try {
                username = JWTUtil.getUsername(accessToken);
            } catch (IllegalArgumentException e) {
                logger.info("cannot decode this jwt code:[" + accessToken + "],which message is :[" + e.getMessage() + "]");
            }
        }
        return new SimplePrincipalCollection(username, SpringUtil.getBean(AuthenRealmBase.class).getName());
    }

    @Override
    public boolean isAuthenticated() {
        return hasPrincipals();
    }
}
