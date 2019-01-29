package com.example.sec.security.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
	//	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		//可以返回json或者重定向到html
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=utf8");
		PrintWriter writer = response.getWriter();
		writer.write("{\"message\":\"onLogoutSuccess\"}");
		writer.flush();
		writer.close();
	}
}
