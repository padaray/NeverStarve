package com.NeverStarve.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.service.MenuService;

@RestController
@RequestMapping("/MenuRest")
public class MenuRestController {

	@Autowired
	MenuService menuService;
	
	@PostMapping("/save")
	public MenuBean save(MenuBean menuBean) {
		
		//用來確認自己有沒有把菜單建立起來，順便去看有無把外鍵設定到菜單裡頭
		//Postman要設定storeBean並且輸入你要關聯的店家的ID
		System.out.println(menuBean);
		return menuService.save(menuBean);
	}
	
	@GetMapping("/getMenuByStoreId/{id}")
	public List<MenuBean> getMenuByStroeId(@PathVariable Integer id){
		
	return menuService.getMenuByStroeId(id);
	}
	
}
