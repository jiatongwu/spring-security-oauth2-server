package com.example.sec.security.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * UsernamePasswordAuthenticationFilter -> AuthenticationManager
 * 管理AuthenticationProvider -> AuthenticationProvider（DaoAuthenticationProvider）
 * -> AbstractUserDetailsAuthenticationProvider ->
 * 
 * 
 * **访问的是否是登录的接口** SecurityContextPersistenceFilter -> SecurityContextHolder ->
 * SecurityContext
 * 
 * Authentication 接口有不同的实现类 不同的AuthenticationProvider 支持不同的Authentication
 * 
 * 
 * 如果请求的是登录接口　不会走FilterSecurityInceptor和ExceptionTranslationFilter
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private MySessionInformationExpiredStrategy mySessionInformationExpiredStrategy;
	@Autowired
	private MySessionInvalidSessionStrategy mySessionInvalidSessionStrategy;
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;
	@Autowired
	private MyAccessDenyHandler myAccessDenyHandler;
	@Autowired
	private MyImageCodeFilterBean myImageCodeFilterBean;
	
	private String loginPage = "/login.do";

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 修正错误保证 AuthenticationFailureHandler子类调用super.onAuthenticationFailure 达到同样的效果
		myAuthenticationFailureHandler.setDefaultFailureUrl(loginPage + "?error");

		// http.httpBasic()
		http.addFilterBefore(myImageCodeFilterBean, UsernamePasswordAuthenticationFilter.class).formLogin().loginPage(loginPage).loginProcessingUrl("/doLogin")
				.successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler).and()
				// session配置
				.sessionManagement()
				// .invalidSessionUrl("/sessionInvalid")
				.invalidSessionStrategy(mySessionInvalidSessionStrategy).maximumSessions(1)
				.maxSessionsPreventsLogin(false).expiredSessionStrategy(mySessionInformationExpiredStrategy).and().and()
				// 退出配置
				.logout().logoutUrl("/logout")
				// .logoutSuccessUrl("")
				.logoutSuccessHandler(myLogoutSuccessHandler).deleteCookies("JSESSIONID").and()
				// 配置授权
				.authorizeRequests()
				//不需要认证就能访问
				.antMatchers(loginPage, "/testAjax/*", "/sessionInvalid","/captchaImage").permitAll()
				// 权限表达式
				// 授权过程分析 未进行身份认证的用户会经过AnonymousAuthenticationFilter生成一个AnonymousAuthentication
				// 拿到securityConfig中的configAttribute,Authentication中的角色和权限信息，当前请求的request信息
				// 通过FilterSecurityInterceptor中AccessDecisionManager实现类
				// 使用多个投票器AccessDecisionVoter进行投票
				// AccessDecisionManager有不同的实现类 根据投票器的投票结果来决定是否通过 不同的实现类通过方式不同
				// AccessDecisionVoter 实现类是WebExpressionVoter
				.antMatchers("/user/*").access("hasRole('USER') and hasRole('ADMIN')")
				// 实现自己的权限表达式
				.antMatchers("/api/**").access("@myRbacService.hasPermission(request,authentication)")
				// 简单角色授权方式
				.antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN").anyRequest().authenticated().and().csrf()
				.disable()
				// 403 无权限返回json
				.exceptionHandling().accessDeniedHandler(myAccessDenyHandler);
	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Producer defaultKaptcha() {
		/*
		 * Kaptcha可配置项
配置项 	描述 	默认值 	可选值
kaptcha.border 	是否有边框 	默认为yes 	yes,no
kaptcha.border.color 	边框颜色 	默认为Color.BLACK 	
kaptcha.border.thickness 	边框粗细度 	默认为1 	
kaptcha.producer.impl 	验证码生成器 	默认为DefaultKaptcha 	
kaptcha.textproducer.impl 	验证码文本生成器 	默认为DefaultTextCreator 	
kaptcha.textproducer.char.string 	验证码文本字符内容范围 		
kaptcha.textproducer.char.length 	验证码文本字符长度 	默认为5 	
kaptcha.textproducer.font.names 	验证码文本字体样式 		
kaptcha.textproducer.font.size 	验证码文本字符大小 	默认为40 	
kaptcha.textproducer.font.color 	验证码文本字符颜色 	默认为Color.BLACK 	
kaptcha.textproducer.char.space 	验证码文本字符间距 	默认为2 	
kaptcha.noise.impl 	验证码噪点生成对象 	默认为DefaultNoise 	
kaptcha.noise.color 	验证码噪点颜色 	默认为Color.BLACK 	
kaptcha.obscurificator.impl 	验证码样式引擎 	默认为WaterRipple 	
kaptcha.word.impl 	验证码文本字符渲染 	默认为DefaultWordRenderer 	
kaptcha.background.impl 	验证码背景生成器 	默认为DefaultBackground 	
kaptcha.background.clear.from 	验证码背景颜色渐进 	默认为Color.LIGHT_GRAY 	
kaptcha.background.clear.to 	验证码背景颜色渐进 	默认为Color.WHITE 	
kaptcha.image.width 	验证码图片宽度 	默认为200 	
kaptcha.image.height 	验证码图片高度 	默认为50

图片样式：
水纹 com.google.code.kaptcha.impl.WaterRipple
鱼眼 com.google.code.kaptcha.impl.FishEyeGimpy
阴影 com.google.code.kaptcha.impl.ShadowGimpy

		 */
		DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
		Properties properties=new Properties();
		properties.put("kaptcha.border","yes");
		properties.put("kaptcha.border.color","105,179,90");
		properties.put("kaptcha.textproducer.font.color","blue");
		properties.put("kaptcha.image.width",80);
		properties.put("kaptcha.image.height",30);
		properties.put("kaptcha.textproducer.font.size",27);
		properties.put("kaptcha.textproducer.char.space", "1");
		properties.put("kaptcha.session.key","code");
		properties.put("kaptcha.textproducer.char.length",3);
		properties.put("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
		properties.put("kaptcha.textproducer.char.string","abcdefghigklmnopqrstuvwxyz1234567890ABCDEFGHIGKLMNOPQRSTUVWXYZ");
		properties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");
		properties.put("kaptcha.noise.color","white");
		//properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
		properties.put("kaptcha.background.clear.from","white");
		properties.put("kaptcha.background.clear.to","white");
		Config config=new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}


}
