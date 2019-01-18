package com.example.sec.security.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private RequestCache requestCache = new HttpSessionRequestCache();
	@Autowired
	private MyProperties myProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			logger.debug("savedRequest->redirectUrl:" + redirectUrl);
		}

		if (LoginType.JSON == myProperties.getLoginType()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=utf8");
			PrintWriter writer = response.getWriter();
			writer.write("{\"message\":\"" + exception.getMessage() + "\"}");
			writer.flush();
			writer.close();
			return;
		}
		super.onAuthenticationFailure(request, response, exception);
	}
}
