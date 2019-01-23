package com.example.sec.security.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class MyRbacService {
	//private AntPathMatcher antPathMatcher=new AntPathMatcher();
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		//antPathMatcher.match(url, request.getRequestURI())
		return false;
	}
}
