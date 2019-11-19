package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;
//http://www.jt.com/cart/show.html
@RequestMapping("/cart")
@Controller
public class CartController {
	@Reference(check = false)
	private DubboCartService dubboCartService;

	@RequestMapping("/show")
	public String show(Model model) {
		Long userId = 7L;
		List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	//	http://www.jt.com/cart/update/num/562379/2
	@RequestMapping("update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		Long userId = 7L;
		cart.setUserId(userId);
		dubboCartService.updataCartNum(cart);
		return  SysResult.success();

	}

	//	http://www.jt.com/cart/delete/562379.html
	@RequestMapping("/delete/{itemId}")
	public String delete(Cart cart) {
		Long userId = 7L;
		cart.setUserId(userId);
		
		dubboCartService.delete(cart);
		return "redirect:/cart/show";

	}
	
	//http://www.jt.com/cart/add/562379.html
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		Long userId = 7L;
		cart.setUserId(userId);
		
		dubboCartService.addCart(cart);
//		/http://www.jt.com/items/562379.html
		return "redirect:/items/"+cart.getItemId();

	}

}
