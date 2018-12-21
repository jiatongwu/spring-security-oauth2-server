package com.example.sec.exception;

public class UserNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6682451157492986691L;
	
	private Integer id;
	public UserNotExistException(Integer id) {
		super("user not exist");
		this.id=id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}
