package com.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.thread.FetchNewRssThread;

public class SpringInit implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	FetchNewRssThread fetchNewRssThread;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		fetchNewRssThread.init();
	}

}
