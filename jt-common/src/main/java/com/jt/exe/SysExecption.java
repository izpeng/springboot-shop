package com.jt.exe;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice  //异常通知,对controller层生效
@Slf4j
public class SysExecption {
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public SysResult error(Exception exception) {
		exception.printStackTrace();
		log.error(exception.getMessage());
		return SysResult.fail();
		
	}
}
