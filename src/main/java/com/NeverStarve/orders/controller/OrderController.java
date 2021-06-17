package com.NeverStarve.orders.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.ShoppingCar;
import com.NeverStarve.orders.service.OrderService;

@Controller
@RequestMapping("/Order")
public class OrderController {
	@Autowired
	OrderService orderservice;
	
	List<ShoppingCar> car;

	@GetMapping("/getOrderById/{id}")
	public Optional<OrderBean> getOrderById(@PathVariable int id) {

		return orderservice.findByPkOrderId(id);
	}
	
	@PostMapping(value =  "/saveShoppingCar",
				//指定回傳內容一定要為JSON
				consumes = MediaType.APPLICATION_JSON_VALUE)
	//讓前端送回JSON給後端可以用@RequestBody
	@ResponseBody
	public void saveShoppingCar(@RequestBody List <ShoppingCar> car) {
		System.out.println(car);
		this.car = car;
	}
	
	
}
