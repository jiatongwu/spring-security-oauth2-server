package com.example.sec.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.example.sec.controller.validator.MyConstraint;
import com.fasterxml.jackson.annotation.JsonView;

public class User {
	public interface UserSimpleView {
	}

	public interface UserDetailView extends UserSimpleView {
	};

	private Integer id;
	@MyConstraint(message = "自定义username　validator")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	@Past(message = "生日必须是过去的日期")
	private Date birthday;

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
