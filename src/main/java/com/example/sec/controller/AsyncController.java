package com.example.sec.controller;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QueueTask queueTask;
	@Autowired
	private DeferredResultHolder deferredResultHolder;

	@GetMapping("/sync")
	public String sync() throws InterruptedException {
		logger.debug("主线程开始");
		Thread.sleep(5000);
		logger.debug("主线程结束");
		return "hello";
	}

	@GetMapping("/callableAsync")
	public Callable<String> callableAsync() throws InterruptedException {
		logger.debug("主线程开始");

		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
				logger.debug("副线程开始");
				Thread.sleep(5000);
				logger.debug("副线程开始");
				return "hello";
			}
		};

		logger.debug("主线程结束");
		return callable;
	}

	@GetMapping("/deferredResultAsync")
	public DeferredResult<String> deferredResultAsync() throws InterruptedException {
		logger.debug("主线程开始");
		DeferredResult<String> deferredResult = new DeferredResult<>();
		String randomUUID = UUID.randomUUID().toString();
		deferredResultHolder.getDeferredResultMap().put(randomUUID, deferredResult);
		queueTask.setOrderNumber(randomUUID);
		logger.debug("主线程结束");
		return deferredResult;
	}

}
