package com.example.sec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
@Component
public class DeferredResultProcessThread implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private QueueTask queueTask;
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {

					String completeOrderNumber = queueTask.getCompleteOrderNumber();
					if (completeOrderNumber != null) {
						logger.debug("有一个任务完成了，返回给浏览器数据");
						DeferredResult<String> deferredResult = deferredResultHolder.getDeferredResultMap()
								.get(completeOrderNumber);
						deferredResult.setResult("hello");
						queueTask.setCompleteOrderNumber(null);

					} else {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}).start();

	}

}
