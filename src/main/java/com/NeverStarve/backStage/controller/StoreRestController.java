package com.NeverStarve.backStage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@RestController
@RequestMapping("/Backstage/Store")
public class StoreRestController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	StoreService storeService;
	
	@PostMapping("/getPageStore/{pageNo}/{address}")
	public Map<Integer, StoreBean> getPageStoreByaddress(@PathVariable String address
			,@PathVariable Integer pageNo){
		
		return storeService.findByAddressContaining(pageNo, address);
	}
	
	@PostMapping("/getPageStore/{pageNo}")
	public Map<Integer, StoreBean> getPageStore(@PathVariable Integer pageNo){ 
		
		
		return storeService.getPageStores(pageNo);
	}
	
}
