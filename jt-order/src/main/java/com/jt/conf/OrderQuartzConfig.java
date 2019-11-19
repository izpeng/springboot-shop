package com.jt.conf;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jt.quartz.OrderQuartz;


@Configuration
public class OrderQuartzConfig {
	
	//定义任务详情
	@Bean
	public JobDetail orderjobDetail() {
		//指定job的名称和持久化保存任务
		return JobBuilder
				.newJob(OrderQuartz.class) //1.自定义任务
				.withIdentity("orderQuartz") //2.任务名称
				.storeDurably()
				.build();
	}
	//定义触发器
	@Bean
	public Trigger orderTrigger() {
		/*SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(1)	//定义时间周期
				.repeatForever();*/
		CronScheduleBuilder scheduleBuilder 
			= CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"); //1.执行周期
		return TriggerBuilder
				.newTrigger()
				.forJob(orderjobDetail())
				.withIdentity("orderQuartz")	//2.任务
				.withSchedule(scheduleBuilder).build();
	}
}
