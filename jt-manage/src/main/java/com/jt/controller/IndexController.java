package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//restful
@Controller
public class IndexController {
	@RequestMapping("/page/{modulName}")
	public String a(@PathVariable(name = "modulName") String modulName) {
		return modulName;
	}
}
                                                                                               