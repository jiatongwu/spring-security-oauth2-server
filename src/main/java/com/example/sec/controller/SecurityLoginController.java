package com.example.sec.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SecurityLoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/login.do")
	public String toLogin(HttpSession session) {

		AuthenticationException lastException = (AuthenticationException) session
				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (lastException instanceof BadCredentialsException) {
			logger.debug("密码错误");
		}
		return "login";
	}

	@GetMapping("/getAuthentication")
	@ResponseBody
	public String toLogin() throws JsonProcessingException {
		// public String toLogin(Authentication authentication) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return objectMapper.writeValueAsString(authentication);
	}
}
