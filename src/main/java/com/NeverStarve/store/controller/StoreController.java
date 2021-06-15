package com.NeverStarve.store.controller;

import java.util.List;

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

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@RequestMapping("/store")
public class StoreController {

	@Autowired StoreService storeService;
	
	//店家註冊頁面
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("storeBean", new StoreBean());
		return "store/register";
	}
	
	//註冊表單輸入
	@PostMapping("/register")
	public String register(@Valid StoreBean storeBean, BindingResult result) {
		
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
	public String loginPage() {
		return "store/login";
	}
	
	//登入帳號
	@PostMapping("/login")
	public String login(@RequestParam String storeAccount,
						@RequestParam String storePassword,
						HttpSession session) {
		StoreBean storeBean = storeService.findByStoreAccountAndStorePassword(storeAccount, storePassword);
		if(storeBean != null) {
			session.setAttribute("storeUser", storeBean);
			return "storeIndex";
		}
		return "store/login";
	}
	
	//登出帳號
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.removeAttribute("storeUser");
			return "store/login";
		}
	
	//確認密碼是否依樣
	public boolean comfirmPassword(StoreBean storeBean) {
		if(storeBean.getStorePassword().equals(storeBean.getStoreCheckPassword())){
			return false;
		}
		return true;
			
	}
}
