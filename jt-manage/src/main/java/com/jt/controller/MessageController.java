package com.jt.controller;
/**
 * 
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MessageController {
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/getPort")
	public String getProt() {
		
		return "当前服务器端口号:"+port;

	}
}
