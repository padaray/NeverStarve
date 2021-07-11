package com.NeverStarve.store.controller;


import java.sql.Blob;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
//	//店家查看訂單 
//	@GetMapping("/storeOrders")
//	public String storeOrders(HttpServletRequest request,Model model) {
//		if(!checkCookie(request, model)) {
//			return "store/login";
//		}
//		return "store/storeOrders";
//	}
	
	//修改店家詳細資料頁面
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
	public String modifyInfo(StoreBean storeBean, BindingResult result) {
	//確認兩次密碼輸入一樣
		if(comfirmPassword(storeBean)) {
			result.rejectValue("storeCheckPassword","confirmError" ,"密碼不一致");
		}	
		//寫入圖片
		MultipartFile storeImage = storeBean.getStoreImage();
		if (storeImage != null && !storeImage.isEmpty()) {
			String ImageFileName = storeImage.getOriginalFilename();
			storeBean.setStoreImageName(ImageFileName);
			try {
				byte[] b = storeImage.getBytes();
				Blob blob = new SerialBlob(b);
				storeBean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}
		//預設店家等級為一
		storeBean.setStoreLv(1);
		storeService.save(storeBean);
		return "redirect:/store/storeIndex";
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
	@GetMapping("/findAll")
	public String findAll(Model model){
	List<StoreBean> storeall = storeService.findAll();
		model.addAttribute("store",storeall);
		return "store/testFindMenu";
	}
	
}
