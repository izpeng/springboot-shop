package com.jt.exe;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice  //异常通知,对controller层生效
@Slf4j
public class SysExecption {
	
	//区分系统正常异常 和 跨域异常
	//跨域访问时一定有callback
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Object error(Exception exception,HttpServletRequest httpServletRequest) {
		String callback = httpServletRequest.getParameter("callback");
		if(StringUtils.isEmpty(callback)) {
			exception.printStackTrace();
			log.error(exception.getMessage());
			return SysResult.fail();
		}else {
			exception.printStackTrace();
			log.error(exception.getMessage());
			return new JSONPObject(callback, SysResult.fail());
		}
		
		
	}
	
	
}
