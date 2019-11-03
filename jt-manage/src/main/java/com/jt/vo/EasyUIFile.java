package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIFile {
	private Integer error=0;
	private String url;
	private Integer width;
	private Integer height;
	
	//简化操作
	public static EasyUIFile fail() {
		return new EasyUIFile(1,null,null,null);
	}
}
