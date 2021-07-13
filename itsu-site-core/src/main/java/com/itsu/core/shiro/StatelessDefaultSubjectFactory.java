package com.itsu.core.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
	@Override
	public Subject createSubject(SubjectContext context) {
		//不创建session
		context.setSessionCreationEnabled(false);

		boolean isNotBasedOnWebSubject = context.getSubject() != null && !(context.getSubject() instanceof WebSubject);
		if (!(context instanceof WebSubjectContext) || isNotBasedOnWebSubject) {
			SecurityManager securityManager = context.resolveSecurityManager();
			Session session = context.resolveSession();
			boolean sessionCreationEnabled = context.isSessionCreationEnabled();
			PrincipalCollection principals = context.resolvePrincipals();
			boolean authenticated = context.resolveAuthenticated();
			String host = context.resolveHost();

			return new DelegatingSubject(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
		}
		WebSubjectContext wsc = (WebSubjectContext) context;
		SecurityManager securityManager = wsc.resolveSecurityManager();
		Session session = wsc.resolveSession();
		boolean sessionEnabled = wsc.isSessionCreationEnabled();
		PrincipalCollection principals = wsc.resolvePrincipals();
		boolean authenticated = wsc.resolveAuthenticated();
		String host = wsc.resolveHost();
		ServletRequest request = wsc.resolveServletRequest();
		ServletResponse response = wsc.resolveServletResponse();
		return new StatelessDelegatingSubject(principals, authenticated, host, session, sessionEnabled,
				request, response, securityManager);
	}
}
