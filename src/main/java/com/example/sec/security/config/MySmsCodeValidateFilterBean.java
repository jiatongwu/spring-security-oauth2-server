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
import com.example.sec.security.config.exception.ValidateSmsCodeException;

@Component
public class MySmsCodeValidateFilterBean extends OncePerRequestFilter {
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (antPathMatcher.match("/doLoginWithSmscode", request.getRequestURI())
				&& StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
			String smsCode = request.getParameter("smsCode");
			Object attribute = sessionStrategy.getAttribute(new ServletWebRequest(request),
					CaptchaController.SESSION_SMSCODE_KEY);
			if (!((smsCode != null) && (StringUtils.equalsIgnoreCase(smsCode, (String) attribute)))) {
				myAuthenticationFailureHandler.onAuthenticationFailure(request, response,
						new ValidateSmsCodeException("短信验证码不正确"));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
