package com.jt.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/*
 * 系统级的vo
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status;
	private String msg;
	private Object data;


	public static SysResult success() {

		return new SysResult(200,null,null);
	}
	public static SysResult success(Object data) {

		return new SysResult(200,null,data);
	}
	public static SysResult success(String msg,Object data) {

		return new SysResult(200,null,data);
	}

	public static SysResult fail() {

		return new SysResult(201,"业务执行失败",null);
	}

}
