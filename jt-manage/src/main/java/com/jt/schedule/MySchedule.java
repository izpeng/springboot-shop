package com.jt.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedule {
    @Scheduled(cron = "0/5 * * * * *")
	public void test1() throws InterruptedException {
		System.out.println("你瞅啥!");
		Thread.sleep(1000);
		System.out.println("瞅你咋地!");
	}
    
//    @Scheduled(cron = "0/10 * * * * *")
//   	public void test2() {
//   		System.out.println("瞅你咋地!");
//   	}
}
