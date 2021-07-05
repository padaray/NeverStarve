package com.NeverStarve.orders.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.StoreBean;

@RestController
@RequestMapping("/Order")
public class RestOrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	MemberService memberService; 
	
	@GetMapping("/find/{id}")
	Optional<OrderBean> findByPkOrderId(int pkOrderId){
		
		return orderService.findByPkOrderId(pkOrderId);
	}
	

	@GetMapping("/findall")
	List<OrderBean> findll(){
		return orderService.findAll();
	}
	

	


}
