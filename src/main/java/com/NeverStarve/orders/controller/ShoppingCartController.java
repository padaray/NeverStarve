package com.NeverStarve.orders.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.service.MenuService;

@Controller
@RequestMapping("/shop")
public class ShoppingCartController {
	
	@Autowired
	MenuService menuService;
	@Autowired
    HttpSession session;	
	
	@GetMapping("/getMenuByStoreId/{id}")
	public String getMenuByStroeId(@PathVariable Integer id,Model model){
		MemberBean member =(MemberBean) session.getAttribute("member");
		List<MenuBean> MenuByStorId = menuService.getMenuByStroeId(id);
		model.addAttribute("menu",MenuByStorId);
		model.addAttribute("member",member);
		return "test/testcrat";
	}
	
	
	
}
