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
	
	@PostMapping(value="/saveMenu")
	public void saveMenu(@ModelAttribute MenuBean menuBean, HttpServletRequest request) {
		StoreBean storeBean = checkCookie(request);
		menuBean.setStoreBean(storeBean);
		menuService.saveMenuList(menuBean);
	}
	
	//修改菜品
	@PostMapping(value="/modifyMenu")
	public void modifyMenu(@ModelAttribute MenuBean menuBean, HttpServletRequest request) {
		StoreBean storeBean = checkCookie(request);
		menuBean.setStoreBean(storeBean);
		//判斷是否有照片傳入
		MultipartFile dishPicture = menuBean.getDishPicture();
		String ImageName = dishPicture.getOriginalFilename();
		System.out.println(menuBean.getDishName());
		System.out.println(menuBean.getDishPrice());
		System.out.println(menuBean.getDishIntroduction());
		if(ImageName.isEmpty()) {
			menuService.saveMenuListNoPic(menuBean);
		}else {
			menuService.saveMenuList(menuBean);
		}
	}
	
	//取消按鈕按下去後，用ID去後端把本來的菜品顯示
	@GetMapping(value="/findByDishId/{pkDishId}")
	public MenuBean findByDishId(@PathVariable int pkDishId) {
		return menuService.getMenuById(pkDishId);
	}
	
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
