package com.example.sec.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// private Logger logger=LoggerFactory.getLogger(getClass());

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.httpBasic()
		http.formLogin().loginPage("/login.do").loginProcessingUrl("/doLogin").and().authorizeRequests()
				.antMatchers("/login.do","/testAjax/*").permitAll().anyRequest().authenticated().and().csrf().disable();
	}

}
