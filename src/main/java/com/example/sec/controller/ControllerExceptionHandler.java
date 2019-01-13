package com.example.sec.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.sec.exception.UserNotExistException;


/**
 *BasicErrorController 
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(UserNotExistException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public Map<String,Object> userNotExist(UserNotExistException e){
		Map<String,Object> result=new HashMap<>();
		result.put("id", e.getId());
		return result;
	}
	
	
}
