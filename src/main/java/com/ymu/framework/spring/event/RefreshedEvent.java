package com.ymu.framework.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class RefreshedEvent implements ApplicationListener<ContextRefreshedEvent>  {

	/**
	 * 当spring容器初始化完成后就会执行该方法
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("-------spring容器初始化完成");
		//spring mvc下避免两次调用该方法
		if (event.getApplicationContext().getParent() == null) {
			System.out.println("-------spring容器初始化完成");
		}
	}

}
