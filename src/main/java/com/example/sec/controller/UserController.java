package com.example.sec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec.dto.User;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping
	@JsonView(User.UserSimpleView.class)
	public List<User> user(String name, @PageableDefault(page = 2, size = 10, sort = "age,asc") Pageable pageable) {
		System.out.println(name);
		List<User> result = new ArrayList<>();
		result.add(new User());
		result.add(new User());
		result.add(new User());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		return result;
	}

	@GetMapping("/{id:\\d+}")
	public User user(@PathVariable String id) {
		User user = new User();
		user.setUsername("tom");
		return user;
	}
}
