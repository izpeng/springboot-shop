package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.User;
import com.jt.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("findAll")
	public String findAll(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("userList", list);
		return "userList";

	}
	
	
	//ajkx
	@RequestMapping("toAjax")
	public String toAjax() {
		return "ajax";
		
	}
	
	@RequestMapping("findAll-ajax")
	@ResponseBody
	public List<User> findAllajax(){
		List<User> list = userService.findAll();
		return list ;
		
	}
	
}
