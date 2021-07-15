package com.NeverStarve.store.controller;


import java.sql.Blob;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.MenuService;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({"storeUser", "menuList"})
@RequestMapping("/store")
public class StoreController {

	@Autowired 
	StoreService storeService;
	
	@Autowired 
	MenuService menuService;
	
	//店家首頁
	@GetMapping("/storeIndex")
	public String storeIndex(HttpServletRequest request, Model model) {
		StoreBean storebean = checkCookie(request, model);
		if(storebean == null) {
			return "store/login";
		}else {
			List<MenuBean> menuList = menuService.getMenuByStoreBean(storebean);
			model.addAttribute("menuList", menuList);
			return "store/storeIndex";
		}
	}
	
	
	//修改店家頁面
	@GetMapping("/modifyInfo")
	public String modifyInfoPage(HttpServletRequest request, Model model) {
		StoreBean storebean = checkCookie(request, model);
		if(storebean == null) {
			model.addAttribute("storeUser", new StoreBean());
		}
		return "store/modifyInfo";
		
	}
	
	
	
	//修改店家詳細資料
	@PostMapping("/modifyInfo")
	public String modifyInfo(@Valid @ModelAttribute("storeUser") StoreBean storeUser, BindingResult result, Model model) {
		model.addAttribute("storeUser", storeUser);
		storeUser.setStoreAddress(storeUser.getStoreCity() + " " + storeUser.getStoreTown() + " " + storeUser.getStoreAddress());
		
		if (result.hasErrors()) {
			return "store/modifyInfo";
		}
		
		//確認兩次密碼輸入一樣
		if(comfirmPassword(storeUser)) {
			result.rejectValue("storeCheckPassword", "confirmError", "密碼不一致");
			return "store/modifyInfo";
		}
		
		//將存進來的菜品種類相加
		String sttp = "";
		List<String> storeTypeL = storeUser.getStoreTypeList();
		if(storeTypeL != null) {
			for(String ST: storeTypeL) {
				sttp += ST + ",";
			}
			String goSttp =  sttp.substring(0, sttp.length()-1);
			storeUser.setStoreType(goSttp);
		}
		
		//預設店家等級為一
		storeUser.setStoreLv(1);
		
		//判斷是否有照片傳入
		MultipartFile storePicture = storeUser.getStoreImage();
		String ImageName = storePicture.getOriginalFilename();
		if(ImageName.isEmpty()) {
			storeService.saveNoPic(storeUser);
		}else {
			storeService.save(storeUser);
		}
		
		return "redirect:/store/storeIndex";
	}
	
	@GetMapping("/findAll")
	public String findAll(Model model){
		List<StoreBean> storeall = storeService.findAll();
		model.addAttribute("store",storeall);
		return "store/testFindMenu";
	}
	
	//確認密碼是否依樣
	public boolean comfirmPassword(StoreBean storeBean) {
		if(storeBean.getStorePassword().equals(storeBean.getStoreCheckPassword())){
			return false;
		}
		return true;
		
	}
	
	//確認有沒有cookie
	public StoreBean checkCookie(HttpServletRequest request, Model model){
		StoreBean storeBean = null;
		Cookie cookies[] = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies) {
				storeBean = storeService.findCookieByStoreAccount(cookie.getValue());
		 		if(storeBean != null) {
		 			model.addAttribute("storeUser", storeBean);
		 			return storeBean;
		 		}
		 	}
		 }
		 return storeBean;
	}
	
}
