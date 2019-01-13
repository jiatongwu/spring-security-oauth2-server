package com.example.sec.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.sec.web.filter.UserControllerFilter;
import com.example.sec.web.interceptor.UserControllerInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private UserControllerInterceptor userControllerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userControllerInterceptor).addPathPatterns("/user/1");
		registry.addInterceptor(userControllerInterceptor).addPathPatterns("/user");
	}

	@Bean
	public FilterRegistrationBean userControllerFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new UserControllerFilter());
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/user/1");
		bean.setUrlPatterns(urlPatterns);
		return bean;
	}
	
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		super.configureAsyncSupport(configurer);
	}
}
