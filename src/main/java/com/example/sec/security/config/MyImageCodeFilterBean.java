package com.example.sec.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sec.controller.CaptchaController;
import com.example.sec.security.config.exception.ValidateImageCodeException;

@Component
public class MyImageCodeFilterBean extends OncePerRequestFilter {
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (antPathMatcher.match("/doLogin", request.getRequestURI())
				&& StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
			String parameter = request.getParameter("imageCode");
			Object attribute = sessionStrategy.getAttribute(new ServletWebRequest(request),
					CaptchaController.SESSION_IMAGECODE_KEY);
			if (!((parameter != null) && (StringUtils.equalsIgnoreCase(parameter, (String) attribute)))) {
				myAuthenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateImageCodeException("验证码不正确"));
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

}
