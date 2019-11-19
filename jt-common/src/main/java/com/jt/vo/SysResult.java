package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *   该类是系统级VO对象.
 * @author Administrator
 *
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status;	//状态码 200成功  201失败
	private String  msg;	//提示信息
	private Object  data;   //返回数据
	
	
	/**
	 * 1.只是告知用户 执行成功
	 * @return
	 */
	public static SysResult success() {
		
		return new SysResult(200, null, null);
	}
	
	/**
	 * 2.成功之后返回数据data   String=name
	 */
	public static SysResult success(Object data) {
		
		return new SysResult(200, null, data);
	}
	
	/**
	 * 3.成功之后返回msg
	 */
	public static SysResult success(String msg,Object data) {
		
		return new SysResult(200, msg, data);
	}
	
	//失败之后调用
	public static SysResult fail(){
		
		return new SysResult(201,"业务执行失败", null);
	}
	
	
	
}
