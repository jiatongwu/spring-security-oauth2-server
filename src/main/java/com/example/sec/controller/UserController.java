package com.example.sec.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec.dto.User;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * BasicErrorController 默认错误处理方式
 *
 */
@RestController
@RequestMapping("/user")
@Api(tags= {"用户接口"})
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
	@ApiOperation("根据id查询用户")
	public User user(@ApiParam("用户id")@PathVariable String id) {
		//if(1==1) {
			//throw new UserNotExistException(Integer.parseInt(id));
		//}
		@SuppressWarnings("unused")
		User user = new User();
		user.setUsername("tom");
		return user;
	}
	@GetMapping("/test")
	public User usertest(){
		//if(1==1) {
			//throw new UserNotExistException(Integer.parseInt(id));
		//}
		@SuppressWarnings("unused")
		User user = new User();
		user.setUsername("tom");
		return user;
	}
	@PostMapping
	@ApiOperation("新增用户")
//	@ApiImplicitParams({ @ApiImplicitParam(name = "anhao", value = "案号"),
//			@ApiImplicitParam(name = "beginDate", value = "创建时间起始于"),
//			@ApiImplicitParam(name = "endDate", value = "创建时间结束于"),
//			@ApiImplicitParam(name = "isCreateTimeAsc", value = "是否按创建时间升序排列,1代表升序0代表降序") })
	public User createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println(fieldError.getField());
				System.out.println(fieldError.getDefaultMessage());
				System.out.println(fieldError.getCode());
			}
		}
		System.out.println(user.getBirthday());
		user.setId(1);
		user.setPassword("password");
		user.setUsername("tom");
		return user;
	}
	

	@PutMapping("/{id:\\d+}")
	public User updateUser(HttpServletResponse response,@PathVariable String id, @Valid @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println(fieldError.getField());
				System.out.println(fieldError.getDefaultMessage());
				System.out.println(fieldError.getCode());
				response.setStatus(401);
			}
		}
		return user;
	}

	@DeleteMapping("/{id:\\d+}")
	public void deleteUser(@PathVariable String id) {
		System.out.println(id);
	}
	
}
