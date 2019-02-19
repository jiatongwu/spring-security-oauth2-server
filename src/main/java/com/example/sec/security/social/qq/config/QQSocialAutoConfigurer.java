package com.example.sec.security.social.qq.config;

import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.example.sec.security.social.qq.connect.QQConnectionFactory;
@Configuration
public class QQSocialAutoConfigurer extends SocialAutoConfigurerAdapter {

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		// return new QQConnectionFactory("qq", new QQServiceProvider("", ""),
		// apiAdapter);
		return new QQConnectionFactory("qq", "101549447", "b74351897dfbbd46cd78a575db9bc1ca");
	}

}
