package com.example.sec.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserControllerAop {

	@Around("execution(* com.example.sec.controller.UserController.user(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("UserControllerAop before");
		Object o = pjp.proceed();
		System.out.println("UserControllerAop after");
		return o;
	}

}
