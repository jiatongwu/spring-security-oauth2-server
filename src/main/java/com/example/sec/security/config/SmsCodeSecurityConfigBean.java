package com.example.sec.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
@Component
public class SmsCodeSecurityConfigBean extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	@Autowired
	private MySmsCodeProvider mySmsCodeProvider;
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
		
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		
		MySmsCodeAuthenticationFilter mySmsCodeAuthenticationFilter=new MySmsCodeAuthenticationFilter();
		mySmsCodeAuthenticationFilter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
		mySmsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
		mySmsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
		
		httpSecurity.authenticationProvider(mySmsCodeProvider).addFilterAfter(mySmsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
