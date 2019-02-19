package com.example.sec.security.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.example.sec.security.social.qq.api.QQApi;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {

	public QQConnectionFactory(String providerId, String appid, String appSecret) {
		super(providerId, new QQServiceProvider(appid, appSecret), new QQApiAdapter());
	}

}
