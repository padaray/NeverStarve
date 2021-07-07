package com.NeverStarve.store.controller;

import java.sql.Blob;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.MenuService;
import com.NeverStarve.store.service.StoreService;

@RestController
@RequestMapping("/store")
public class MenuRestController {

	@Autowired
	MenuService menuService;
	
	@Autowired
	StoreService storeService;
	
	@PostMapping("/save")
	public MenuBean save(MenuBean menuBean) {
		
		//用來確認自己有沒有把菜單建立起來，順便去看有無把外鍵設定到菜單裡頭
		//Postman要設定storeBean並且輸入你要關聯的店家的ID
		System.out.println(menuBean);
		return menuService.save(menuBean);
	}
	
	//刪除單一品項
	@GetMapping("/delete/{dishId}")
	public void deleteByStoreId(@PathVariable Integer dishId) {
		menuService.deleteByDishId(dishId);
	}
	
	
//	//新增菜品
//	@PostMapping(value="/saveMenu", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public void saveMenu(@RequestBody List<MenuBean> menuListS, HttpServletRequest request) {
//		StoreBean storeBean = checkCookie(request);
//		menuService.saveMenuList(menuListS, storeBean);
//	}
	
	@PostMapping(value="/saveMenu")
	public void saveMenu(@ModelAttribute MenuBean menuBean, HttpServletRequest request) {
		StoreBean storeBean = checkCookie(request);
		menuBean.setStoreBean(storeBean);
		menuService.saveMenuList(menuBean);
	}
	
	//修改菜品
	@PostMapping(value="/modifyMenu", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void modifyMenu(@RequestBody MenuBean menuBean, HttpServletRequest request) {
		StoreBean storeBean = checkCookie(request);
		if(storeBean != null) {
			menuBean.setStoreBean(storeBean);
			menuService.save(menuBean);
		}
	}
	
	//取消按鈕按下去後，用ID去後端把本來的菜品顯示
	@GetMapping(value="/findByDishId/{pkDishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public MenuBean findByDishId(@PathVariable int pkDishId) {
		return menuService.getMenuById(pkDishId);
	}
	
//	@GetMapping("/getMenu")
//	public List<MenuBean> getMenu(HttpServletRequest request, Model model){
//		model.addAttribute("storeUser", new StoreBean());  //初始化model的storeUser
//		checkCookie(request, model);  //用cookie建立model
//		//把抓到的storeId丟給後端抓菜單
//		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
//		return menuService.getMenuByStoreBean(storeBean);
////		model.addAttribute("menuList", menuList);
//	}
	
	//確認有沒有cookie
		public StoreBean checkCookie(HttpServletRequest request){
			Cookie cookies[] = request.getCookies();
			if(cookies != null){
				for(Cookie cookie : cookies) {
					StoreBean storeBean = storeService.findCookieByStoreAccount(cookie.getValue());
			 		if(storeBean != null) {
			 			return storeBean;
			 		}
			 	}
			 }
			 return null;
		}
	
//	@GetMapping("/getMenuByStoreId/{id}")
//	public List<MenuBean> getMenuByStroeId(@PathVariable Integer id,Model model){
//		List<MenuBean> MenuByStorId = menuService.getMenuByStroeId(id);
//		model.addAttribute("menu",MenuByStorId);
//	return menuService.getMenuByStroeId(id);
//	}
//	
//	@GetMapping("/getMenuByStoreId/{id}")
//	public String getMenuByStroeId(@PathVariable Integer id,Model model){
//		List<MenuBean> MenuByStorId = menuService.getMenuByStroeId(id);
//		model.addAttribute("menu",MenuByStorId);
//		return "test/testcrat";
//	}
	
	
}
