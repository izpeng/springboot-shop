package com.jt.service;

import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service
public class DubboCartServiceImpl implements DubboCartService{
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		List<Cart> cartList = cartMapper.selectList(queryWrapper);
		return cartList;
	}

	@Override
	public void updataCartNum(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		Cart cartTemp = new Cart();
		cartTemp.setNum(cart.getNum())
		.setUpdated(new Date());

		queryWrapper.eq("user_id", cart.getUserId()) ;  
		queryWrapper.eq("item_id", cart.getItemId()) ;  
		cartMapper.update(cartTemp, queryWrapper);

	}

	@Override
	public void delete(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();

		queryWrapper.eq("user_id", cart.getUserId()) ;  
		queryWrapper.eq("item_id", cart.getItemId()) ;  
		cartMapper.delete(queryWrapper);	
	}

	@Override
	public void addCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", cart.getUserId()) ;  
		queryWrapper.eq("item_id", cart.getItemId()) ;  
		Cart selectOne = cartMapper.selectOne(queryWrapper);

		if(selectOne==null) {
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);
			
		}else {
			UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
			Cart cartTemp = new Cart();
			cartTemp.setNum((Integer)(selectOne.getNum()+cart.getNum()))
			.setUpdated(new Date());

			updateWrapper.eq("user_id", selectOne.getUserId()) ;  
			updateWrapper.eq("item_id", selectOne.getItemId()) ;  
			cartMapper.update(cartTemp, updateWrapper);

		}
		
	}



}
