package com.example.sec.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="wu.conf")
public class MyProperties {
	
	
	private String my01;
	private BrowerProperties brower=new BrowerProperties();
	public String getMy01() {
		return my01;
	}
	public void setMy01(String my01) {
		this.my01 = my01;
	}
	public BrowerProperties getBrower() {
		return brower;
	}
	public void setBrower(BrowerProperties brower) {
		this.brower = brower;
	}
	
	

}
