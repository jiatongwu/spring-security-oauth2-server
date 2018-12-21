package com.example.sec.controller.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.sec.service.HelloService;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

	@Autowired
	private HelloService helloService;
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		System.out.println("my constraintValidator init");
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		System.out.println(helloService.hello());
		System.out.println("my constraintValidator isValid");
		return true;
	}

}
