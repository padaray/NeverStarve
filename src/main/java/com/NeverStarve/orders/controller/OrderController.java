package com.NeverStarve.orders.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.service.OrderService;

@RestController
@RequestMapping("/Order")
public class OrderController {
	@Autowired
	OrderService orderservice;

	@GetMapping("/getOrderById/{id}")
	public Optional<OrderBean> getOrderById(@PathVariable int id) {

		return orderservice.getOrderById(id);
	}
	
}
