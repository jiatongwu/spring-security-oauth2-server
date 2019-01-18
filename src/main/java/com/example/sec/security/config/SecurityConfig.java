package com.example.sec.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * UsernamePasswordAuthenticationFilter -> AuthenticationManager
 * 管理AuthenticationProvider -> AuthenticationProvider（DaoAuthenticationProvider） -> AbstractUserDetailsAuthenticationProvider -> 
 * 
 * 
 * **访问的是否是登录的接口**
 * SecurityContextPersistenceFilter -> SecurityContextHolder -> SecurityContext
 * 
 * Authentication 接口有不同的实现类 不同的AuthenticationProvider 支持不同的Authentication
 * 
 * 
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	private String loginPage = "/login.do";

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 修正错误保证 AuthenticationFailureHandler子类调用super.onAuthenticationFailure 达到同样的效果
		myAuthenticationFailureHandler.setDefaultFailureUrl(loginPage + "?error");

		// http.httpBasic()
		http.formLogin().loginPage(loginPage).loginProcessingUrl("/doLogin")
				.successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler).and()
				.authorizeRequests().antMatchers(loginPage, "/testAjax/*").permitAll().anyRequest().authenticated()
				.and().csrf().disable();
	}

}
