package com.example.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestJspController {
	//private Logger logger=LoggerFactory.getLogger(getClass());
	@GetMapping("/testjsp")
	public String hello() {
		return "index";
	}

}
