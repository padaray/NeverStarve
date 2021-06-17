package com.NeverStarve.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.service.MenuService;

@Controller
@RequestMapping("/shop")
public class ShoppingCartController {
	
	@Autowired
	MenuService menuService;
	
	@GetMapping("/testcart")
	public String test () {
		
		
	return "test/testcrat";
	}
	
	
	@GetMapping("/getMenuByStoreId/{id}")
	public String getMenuByStroeId(@PathVariable Integer id,Model model){
		List<MenuBean> MenuByStorId = menuService.getMenuByStroeId(id);
		model.addAttribute("menu",MenuByStorId);
		return "test/testcrat";
	}
	
	
	
}
