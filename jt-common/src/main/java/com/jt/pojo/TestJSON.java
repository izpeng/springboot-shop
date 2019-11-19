package com.jt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
//忽略未知属性.
@JsonIgnoreProperties(ignoreUnknown = true)
@Data //set/get
public class TestJSON {
	
	private Integer id;
	private String name;
	private Integer age;
	
	public String getXXX() {
		
		return "我是谁?? ..........   臭臭泥";
	}
	
	//json转化为对象时调用Set方法
	/*
	 * public void setXXX(String XXX) {
	 * 
	 * System.out.println("调用Set方法"); }
	 */
}
