package com.NeverStarve.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@RestController
@RequestMapping("/StoreRest")
public class StoreRestController {
	
	@Autowired
	StoreService storeService; 
	
	@PostMapping("/save")
	public StoreBean save(StoreBean storeBean) {
		return storeService.save(storeBean) ;
	}
	

}
