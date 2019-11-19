package com.jt.quartz;


import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;


//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 超时:当前时间 - 创建订单的时间> 30分钟 
	 * 		创建时间 < 当前时间-30分钟
	 *  
	 * 业务:如果超时30分钟.则将状态由1改为6
	 * sql: update tb_order set status=6,updated=#{date}
	 * 		where status = 1 and created < #{timeOut}
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//1.计算超时时间  日历工具API 用于计算时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		Date timeOut = calendar.getTime(); //获取超时时间
		
		Order orderTemp = new Order();
		orderTemp.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1)
					 .lt("created", timeOut);
		orderMapper.update(orderTemp, updateWrapper);
		System.out.println("定时任务操成功!!!!!");
	}
	
}
