package com.NeverStarve.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
public class HomeController {
	
	@Autowired
	StoreService storeSerivce;
	
	@GetMapping("/")
	public String index(Model  model) {
		model.addAttribute("imgurl", "../static/images/NeverStarvelogo3.png");
		
		//搜尋所有店家資料，用來呈現到首頁上
		List<StoreBean> storeAll = storeSerivce.findAll();
		model.addAttribute("stores",storeAll);
		
		return "index";
	}
	
}
