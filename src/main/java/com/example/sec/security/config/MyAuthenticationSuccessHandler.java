package com.example.sec.security.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MyProperties myProperties;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			logger.debug("savedRequest->redirectUrl:" + redirectUrl);
		}
		if (LoginType.JSON == myProperties.getLoginType()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=utf8");
			PrintWriter writer = response.getWriter();
			writer.write(objectMapper.writeValueAsString(authentication));
			writer.flush();
			writer.close();
			return;
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
