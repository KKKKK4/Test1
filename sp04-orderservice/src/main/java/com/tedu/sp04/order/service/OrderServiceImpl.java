package com.tedu.sp04.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.pojo.Order;
import com.tedu.sp01.pojo.User;
import com.tedu.sp01.service.ItemService;
import com.tedu.sp01.service.OrderService;
import com.tedu.sp01.service.UserService;
import com.tedu.sp04.order.feignclient.ItemFeignService;
import com.tedu.sp04.order.feignclient.UserFeignService;
import com.tedu.web.util.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private ItemFeignService itemService;
	
	@Autowired
	private UserFeignService userService;
	@Override
	public Order getOrder(String orderId) {
		
		JsonResult<User> user = userService.getUser(7);
		
		JsonResult<List<Item>> items = itemService.getItems(orderId);
	Order order = new Order();
	order.setId(orderId);
	order.setUser(user.getData());
	order.setItems(items.getData());
		return order;
	}

	@Override
	public void addOrder(Order order) {
		itemService.decreaseNumber(order.getItems());
		
		userService.addScore(7, 100);

		log.info("保存订单:"+order);
	}

}
