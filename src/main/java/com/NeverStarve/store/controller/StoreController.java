package com.NeverStarve.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		if(comfirmPassword(storeBean)) {
			result.rejectValue("兩次密碼不一致", "確認密碼失敗");
		}
		
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
		return "";
	}
	
	//確認密碼是否依樣
	public boolean comfirmPassword(StoreBean storeBean) {
		if(storeBean.getStorePassword().equals(storeBean.getStoreCheckPassword())){
			return false;
		}
		return true;
			
	}
}
