package com.example.sec.security.config.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateImageCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2311603491071716493L;

	public ValidateImageCodeException(String msg) {
		super(msg);
	}

}
