package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITree implements Serializable{

	private static final long serialVersionUID = -1665037633243881859L;
	private Long id;
	private String text;
	private String state;


}
