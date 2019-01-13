package com.example.sec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 配合DeferredResult完成多线程的处理
 * 
 * @author wu
 *
 */
@Component
public class QueueTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String orderNumber;
	private String completeOrderNumber;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		new Thread(() -> {
			try {
				logger.debug("新增一个任务：" + orderNumber);
				Thread.sleep(5000);
				this.completeOrderNumber = orderNumber;
				this.orderNumber = null;
				logger.debug("完成一个任务：" + orderNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		;

	}

	public String getCompleteOrderNumber() {
		return completeOrderNumber;
	}

	public void setCompleteOrderNumber(String completeOrderNumber) {
		this.completeOrderNumber = completeOrderNumber;
	}

}
