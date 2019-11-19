package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//[{id:"编号",text:"文本信息",state:"open/closed"}]
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
	private Long id;		//节点ID
	private String text;	//文本信息
	private String state;	//open/closed
	
}
