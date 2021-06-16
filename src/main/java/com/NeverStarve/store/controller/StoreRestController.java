package com.NeverStarve.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@RequestMapping("/StoreRest")
public class StoreRestController {
	
	@Autowired
	StoreService storeService; 
	
	@PostMapping("/save")
	public StoreBean save(StoreBean storeBean) {
		return storeService.save(storeBean) ;
	}
	
	@GetMapping("/findById/{id}")
	public Optional<StoreBean> findById(@PathVariable int id) {
		
		return storeService.findoneById(id);
	}
	
	@GetMapping("/findAll")
	public String findAll(Model model){
	List<StoreBean> storeall = storeService.findAll();
		model.addAttribute("store",storeall);
		return "store/testFindMenu";
	}

}
