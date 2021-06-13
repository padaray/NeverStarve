package com.NeverStarve.orders.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.service.OrderService;

@RestController
@RequestMapping("/Order")
public class RestOrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping("/find/{id}")
	Optional<OrderBean> findByPkOrderId(int pkOrderId){
		
		return orderService.findByPkOrderId(pkOrderId);
	}
	
	@PostMapping("/save")
	OrderBean save(OrderBean orderBean) {
	
		return orderService.save(orderBean);
	}
	@GetMapping("/findall")
	List<OrderBean> findll(){
		return orderService.findAll();
	}


}
