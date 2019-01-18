package com.example.sec.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//@Component
public class UserControllerFilter implements Filter {
	//private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("UserControllerFilter before");
		chain.doFilter(request, response);
		System.out.println("UserControllerFilter after");
	}

	@Override
	public void destroy() {
		

	}

}
