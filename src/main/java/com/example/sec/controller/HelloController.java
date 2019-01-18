package com.example.sec.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec.security.config.MyProperties;

@RestController
public class HelloController {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private MyProperties myProperties;
	@GetMapping("/hello")
	public String hello() {
		logger.debug(myProperties.getMy01());
		logger.debug(myProperties.getBrower().getBrow01());
		logger.debug(myProperties.getBrower().getBrow02());
		return "hello,world!";
	}

//	@RequestMapping("/test")
//	public String test(String username) throws IOException {
//		System.out.println(username);
//		
//		return "hello";
//	}
	/**
	 * http://127.0.0.1:8080/jsonp?callback=hello
	 * @param username
	 * @return
	 * @throws IOException
	 */	
	@RequestMapping("/jsonp")
	public String jsonp(String callback)  {
		
		return "hello(111)";
	}
}
