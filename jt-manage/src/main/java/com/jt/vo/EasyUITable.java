package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable implements Serializable{
	
	/**
	 * 实行不能随意定义,必须满足js数据要求
	 */
	private Integer total;
	private List<?> rows;
}
