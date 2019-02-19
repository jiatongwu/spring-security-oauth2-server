package com.example.sec.security.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.example.sec.security.social.qq.api.QQApi;
import com.example.sec.security.social.qq.api.QQApiImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

	private String appId;

	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}

	@Override
	public QQApi getApi(String accessToken) {
		return new QQApiImpl(accessToken, appId);
	}

}
