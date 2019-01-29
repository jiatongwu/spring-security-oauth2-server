package com.example.sec.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Component
public class MySmsCodeProvider implements AuthenticationProvider {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		MySmsCodeAuthenticationToken authenticationToken = (MySmsCodeAuthenticationToken) authentication;
		//根据手机号查找用户　后期可以改成根据手机号查找用户名　再根据用户名查找用户　等等实现方案
		UserDetails user = myUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

		if (user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		MySmsCodeAuthenticationToken authenticationResult = new MySmsCodeAuthenticationToken(user, user.getAuthorities());
		
		authenticationResult.setDetails(authenticationToken.getDetails());

		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {		
		return MySmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public MyUserDetailsService getMyUserDetailsService() {
		return myUserDetailsService;
	}

	public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}
	

}
