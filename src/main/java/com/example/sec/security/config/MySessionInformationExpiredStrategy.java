package com.example.sec.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
@Component
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy{
	//private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

		//返回给浏览器set-Cookie jsessionId
		event.getRequest().getSession();
		
		
		// 根据请求的url 是接口还是html 返回相应的数据还是返回html页面
		//redirectStrategy.sendRedirect(request, response, "/login.do");
		HttpServletResponse response = event.getResponse();
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE+";charset=UTF-8");
		response.getWriter().write("{\"message\":\"onExpriedSessionDetected 被踢下去时会触发\"}");
	}

}
