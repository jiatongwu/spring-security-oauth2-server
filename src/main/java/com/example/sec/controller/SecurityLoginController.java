package com.example.sec.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SecurityLoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ObjectMapper objectMapper;

	private RequestCache requestCache = new HttpSessionRequestCache();

	// private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	/**
	 * 当用户未认证时访问接口 会被302到此接口 此接口拿到用户访问的接口url 判断是ajax请求还是html请求 返回json或login.html
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/login.do")
	public String toLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		AuthenticationException lastException = (AuthenticationException) session
				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (lastException instanceof BadCredentialsException) {
			logger.debug("密码错误");
		}
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {

			String redirectUrl = savedRequest.getRedirectUrl();
			logger.debug("savedRequest-> redirectUrl:" + redirectUrl);
			if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
				// redirectStrategy.sendRedirect(request, response, redirectUrl);
				return "login";
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=utf8");
		PrintWriter writer = response.getWriter();
		writer.write("{\"message\":\"请重新登录\"}");
		writer.flush();
		writer.close();
		return null;
	}

	@GetMapping("/getAuthentication")
	@ResponseBody
	public String toLogin() throws JsonProcessingException {
		// public String toLogin(Authentication authentication) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return objectMapper.writeValueAsString(authentication);
	}

	@GetMapping("/sessionInvalid")
	@ResponseBody
	public String sessionInvalid() throws JsonProcessingException {
		// public String toLogin(Authentication authentication) {

		return "{\"message\":\"session失效\"}";
	}
}
