package com.example.sec.service.impl;

import org.springframework.stereotype.Service;

import com.example.sec.service.HelloService;
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello() {
		
		return "hello";
	}

}
