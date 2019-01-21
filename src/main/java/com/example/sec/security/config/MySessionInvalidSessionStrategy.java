package com.example.sec.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class MySessionInvalidSessionStrategy implements InvalidSessionStrategy {
	//private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//返回给浏览器set-Cookie jsessionId
		request.getSession();

		// 根据请求的url 是接口还是html 返回相应的数据还是返回html页面
		//redirectStrategy.sendRedirect(request, response, "/login.do");
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		response.getWriter().write("{\"message\":\"onInvalidSessionDetected session超时时会触发还有其他情况也会触发\"}");

	}

}
