package com.NeverStarve.store.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreOrderListService;
import com.NeverStarve.store.service.StoreOrderService;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({"orderListList"})
@RequestMapping("/store")
public class StoreOrderListController {
	@Autowired 
	StoreOrderService orderService;
	@Autowired 
	StoreService storeService;
	@Autowired 
	StoreOrderListService storeOrderListService;
	@GetMapping("/orderList")
	public String getOrderListByStoreBean(HttpServletRequest request, Model model) {
		model.addAttribute("storeUser", new StoreBean());  //初始化model的storeUser
		checkCookie(request, model);  //用cookie建立model
		//把抓到的storeId丟給後端抓菜單
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		Map<String ,Integer> resultMap= storeOrderListService.getOrderListByStoreBean(storeBean);
		model.addAttribute("resultMap", resultMap);
		System.out.println(resultMap);
		return "store/orderList";
	}
	
	public boolean checkCookie(HttpServletRequest request, Model model){
		Cookie cookies[] = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies) {
				StoreBean storeBean = storeService.findCookieByStoreAccount(cookie.getValue());
		 		if(storeBean != null) {
		 			model.addAttribute("storeUser", storeBean);
		 			return true;
		 		}
		 	}
		 }
		 return false;
	}
}
