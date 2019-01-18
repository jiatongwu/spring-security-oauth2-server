package com.example.sec.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
@Component
public class DeferredResultHolder {
	//private Logger logger=LoggerFactory.getLogger(getClass());

	private Map<String, DeferredResult<String>> deferredResultMap = new HashMap<>();

	public Map<String, DeferredResult<String>> getDeferredResultMap() {
		return deferredResultMap;
	}

	public void setDeferredResultMap(Map<String, DeferredResult<String>> deferredResultMap) {
		this.deferredResultMap = deferredResultMap;
	}

}
