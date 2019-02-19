package com.example.sec.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 当进行usernamepasswordAuthenticationFilter时 > SecurityManager > SecurityProvider
 * > DaoDetailsService > 调用此处 获取用户信息
 * 
 * @author wu
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PasswordEncoder passwordEncoder;
	// private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("用户" + username + " loadUserByUsername");
		UserDetails userDetails = new User("wu", passwordEncoder.encode("1234567"), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ACTUATOR"));
		return userDetails;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		// 根据userId查询用户表
		SocialUserDetails socialUserDetails = new SocialUser(userId, passwordEncoder.encode("1234567"), true, true, true,
				true, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ACTUATOR"));
		return socialUserDetails;
	}

}
