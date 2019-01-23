package com.example.sec.security.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
@Component
public class MyAccessDenyHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//返回html或json
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=utf8");
		PrintWriter writer = response.getWriter();
		writer.write("{\"message\":\"你无权返回此url\"}");
		writer.flush();
		writer.close();
	}

}
