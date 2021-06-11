package com.NeverStarve.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.service.OrderListService;

@RestController
@RequestMapping("/OrderList")
public class RestOrderListController {

	@Autowired
	OrderListService orderListService;
	
	@PostMapping("/save")
	OrderListBean save(OrderListBean orderListBean) {
		
		return orderListService.save(orderListBean);
	}
}
