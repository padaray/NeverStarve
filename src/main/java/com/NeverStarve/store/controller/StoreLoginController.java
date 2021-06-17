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
public class StoreLoginController {

	@Autowired StoreService storeService;
	
	//店家註冊頁面
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("storeBean", new StoreBean());
//		StoreBean storeBean = new StoreBean();
		return "store/register";
	}
	
	//註冊表單輸入
	@PostMapping("/register")
	public String register(@Valid StoreBean storeBean, BindingResult result) {
		
		//地址字串相加
		storeBean.setStoreAddress(storeBean.getStoreCity() + storeBean.getStoreTown() + storeBean.getStoreAddress());
		
		//確認帳號是否存在
		if (storeService.accountExist(storeBean.getStoreAccount())) {
			result.rejectValue("storeAccount", "", "帳號申請重複");
		}
		//確認兩次密碼輸入一樣
		if(comfirmPassword(storeBean)) {
			result.rejectValue("兩次密碼不一致", "確認密碼失敗");
		}
		//確認validator有沒有抱錯
		if(result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			for(FieldError error : fieldErrors) {
				System.out.println(error.getField() + ":" + error.getDefaultMessage() + ":" + error.getCode());
			}
			return "store/register";
		}
		storeService.save(storeBean);
		return "store/login";
	}
	
	//登入頁面頁面
	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, Model model) {
		model.addAttribute("storeBean", new StoreBean());
		if(checkCookie(request, model)) {
			return "redirect:/store/storeIndex";
		}
		return "store/login";
	}
	
	//登入帳號
	@PostMapping("/login")
	public String login(@RequestParam String storeAccount,
						@RequestParam String storePassword,
						HttpServletRequest request,
						HttpServletResponse response,
						Model model) {
		StoreBean storeBean = storeService.findByStoreAccountAndStorePassword(storeAccount, storePassword);
		if(storeBean != null) {
			model.addAttribute("storeUser", storeBean);
		}else {
			return "store/login";
		}
		//給cookie
		processCookies(storeAccount, storePassword, request, response);
		return "redirect:/store/storeIndex";
		
	}
	
	//登出帳號
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
		if(model.getAttribute("storeUser") != null) {
			session.removeAttribute("storeUser");
		}
		
		if(checkCookie(request, model)) {
			deleteCookie(request, response);
		}
		return "redirect:/store/login";
	}
	
	//確認密碼是否依樣
	public boolean comfirmPassword(StoreBean storeBean) {
		if(storeBean.getStorePassword().equals(storeBean.getStoreCheckPassword())){
			return false;
		}
		return true;
			
	}
	
	//給登入商家cookie
	private void processCookies(String storeAccount, String storePassword, HttpServletRequest request , HttpServletResponse response) {
		Cookie cookieAccount = null;
		Cookie cookiePassword = null;
		
		cookieAccount = new Cookie("account", storeAccount);
		cookieAccount.setMaxAge(2 * 60 * 60);       // Cookie的存活期: 2小時
		cookieAccount.setPath(request.getContextPath());	//cookie設置路徑(抓到首頁NeverStarve前)

//		String encodePassword = GlobalService.encryptString(password);
		cookiePassword = new Cookie("password", storePassword);
		cookiePassword.setMaxAge( 2 * 60 * 60);       // Cookie的存活期: 2小時
		cookiePassword.setPath(request.getContextPath());
		
		//給前段創立cookie
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);

	}
	
	//刪除cookie
	private void deleteCookie(HttpServletRequest request , HttpServletResponse response) {
		Cookie cookieAccount = null;
		Cookie cookiePassword = null;
		
		String account = "";
		String password = "";
		
		cookieAccount = new Cookie("account", account);
		cookieAccount.setMaxAge(0);       // Cookie的存活期: 2小時
		cookieAccount.setPath(request.getContextPath());	//cookie設置路徑(抓到首頁NeverStarve前)
		
		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge(0);       // Cookie的存活期: 2小時
		cookiePassword.setPath(request.getContextPath());
		
		//給前段創立cookie
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);

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
