package com.NeverStarve.store.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.member.model.LoginBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({"storeUser"})
@RequestMapping("/store")
public class StoreController {

	@Autowired StoreService storeService;
	
	@GetMapping("/storeIndex")
	public String storeIndex() {
		return "store/storeIndex";
	}
	
	
	//確認有沒有cookie
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
