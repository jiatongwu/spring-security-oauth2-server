package com.example.sec.security.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
	}
//打断点调试　OAuth2AuthenticationService -> socialAuthenticationFilter -> socialAuthenticationProvider -> 
	@Bean
	public SpringSocialConfigurer springSocialConfigurer() {
		//修改默认的/auth/qq 中的auth
//		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
//		ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
//		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
//		return configurer;
		SpringSocialConfigurer springSocialConfigurer= new SpringSocialConfigurer();
		springSocialConfigurer.signupUrl("/register.html");
		return springSocialConfigurer;
	}
	
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}

}
