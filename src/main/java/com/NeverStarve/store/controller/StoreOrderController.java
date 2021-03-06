package com.NeverStarve.store.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreOrderService;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({"orderList"})
@RequestMapping("/store")
public class StoreOrderController {

@Autowired StoreOrderService orderService;
@Autowired StoreService storeService;

@GetMapping("/order")
public String getOrderByStoreBean(HttpServletRequest request, Model model) {
	
	model.addAttribute("storeUser", new StoreBean());  //初始化model的storeUser
	checkCookie(request, model);  //用cookie建立model
	//把抓到的storeId丟給後端抓菜單
	StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
	List<OrderBean> orderList= orderService.getOrderByStoreBean(storeBean);
	model.addAttribute("orderList", orderList);
	return "store/storeOrder";
}

@GetMapping("changeConfirm/{orderId}/{confirm}")
@ResponseBody
public String changeLV(@PathVariable Integer orderId,@PathVariable Integer confirm) {
	String msg;
	if(orderService.changeConfirm(orderId, confirm)) {
		msg = "1";
	}else {
		msg = "0";
	}
	
	return msg;
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
