package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIFile {
	private Integer error=0;	//判断是否有误
	private String url;			//图片存储地址
	private Integer width;		//图片宽度
	private Integer height;		//图片高度
	
	//为了简化操作,可以提供静态方法
	public static EasyUIFile fail() {
		
		return new EasyUIFile(1, null, null, null);
	}
	
}
