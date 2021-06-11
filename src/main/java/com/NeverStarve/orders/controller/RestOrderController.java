package com.NeverStarve.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.StoreBean;

@RestController
@RequestMapping("/Order")
public class RestOrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping("/findById/{id}")
	List<OrderBean> findBypkOrderId (@PathVariable Integer id) {

		return orderService.findBypkOrderId(id);
	}
	
	
	@GetMapping("/findall")
	List<OrderBean> findall (){
		
		return orderService.getAll();
	}

	@PostMapping("/save")
	OrderBean save(OrderBean orderBean) {
		
		return orderService.save(orderBean);
	}

}
