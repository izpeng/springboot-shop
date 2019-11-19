package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试集群是否可用
 * @author Administrator
 *
 */
@RestController
public class MessageController {
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/getPort")
	public String getMsg() {
		
		return "当前服务器端口号为:"+port;
	}
}
