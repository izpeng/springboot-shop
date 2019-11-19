package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

//jt-web中购物车业务 消费者
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(check = false) //提供者可以懒加载
	private DubboCartService cartService;
	
	/**
	 * 查询购物车记录,在页面中展现数据
	 * 页面取值:${cartList}
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model,HttpServletRequest request) {
		
		//1.获取当前用户信息 userId
		//User user = (User) request.getAttribute("JT_USER");
		//Long userId = user.getId();
		Long userId = UserThreadLocal.getUser().getId();
		//2.查询数据
		List<Cart> cartList = 
			cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		Long userId = UserThreadLocal.getUser().getId();
		cart.setUserId(userId);
		cartService.updateCartNum(cart);
		return SysResult.success();
	}
	
	/**
	 * 实现购物车删除
	 * @param cart
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId = UserThreadLocal.getUser().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		
		//伪静态实现思想
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		Long userId = UserThreadLocal.getUser().getId();
		cart.setUserId(userId);
		cartService.insertCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
}
