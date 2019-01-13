package com.example.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestJspController {
	@GetMapping("/testjsp")
	public String hello() {
		return "index";
	}

}
