package com.NeverStarve.orders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShoppingCartController {
	
	@GetMapping("/testcart")
	public String test () {
		
		
	return "test/testcrat";
	}
	
}
