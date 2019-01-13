package com.example.sec.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
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
