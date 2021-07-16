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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.NeverStarve.member.model.LoginBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({"storeUser", "loginBean", "storeBean"})
@RequestMapping("/store")
public class StoreLoginController {

	@Autowired
	StoreService storeService;

	// 店家註冊頁面
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("storeBean", new StoreBean());
//		StoreBean storeBean = new StoreBean();
		return "store/register";
	}

	// 註冊表單輸入
	@PostMapping("/register")
	public String register(@Valid StoreBean storeBean, BindingResult result, Model model) {
		
		model.addAttribute("storeBean", storeBean);
		// 地址字串相加
		storeBean.setStoreAddress(storeBean.getStoreCity() + " " + storeBean.getStoreTown() + " " + storeBean.getStoreAddress());

		// 確認帳號是否存在
		if (storeService.accountExist(storeBean.getStoreAccount())) {
			result.rejectValue("storeAccount", "", "帳號申請重複");
		}
		// 確認兩次密碼輸入一樣
		if (comfirmPassword(storeBean)) {
			result.rejectValue("storeCheckPassword", "confirmError", "密碼不一致");
		}
		// 確認validator有沒有抱錯
		if (result.hasErrors()) {
			return "store/register";
		}

		// 預設店家等級為一
		storeBean.setStoreLv(1);

		//將存進來的菜品種類相加
		String sttp = "";
		List<String> storeTypeL = storeBean.getStoreTypeList();
		if(storeTypeL != null) {
			for(String ST: storeTypeL) {
				sttp += ST + ",";
			}
			String goSttp =  sttp.substring(0, sttp.length()-1);
			storeBean.setStoreType(goSttp);
		}

		storeService.save(storeBean);
		return "redirect:/store/login";
	}

	// 進入登入的頁面
	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, Model model) {
		model.addAttribute("loginBean", new LoginBean());
		if (checkCookie(request, model)) {
			return "redirect:/store/storeIndex";
		}
		return "/Member/login";
	}

	// 登入帳號
	@PostMapping("/login")
	public String login(@Valid LoginBean loginBean, BindingResult result, HttpServletRequest request, 
						HttpServletResponse response, Model model) {
		if (result.hasErrors()) {
			return "Member/login";
		}
		
		String storeAccount = loginBean.getEmail();
		String storePassword = loginBean.getPassword();
		
		StoreBean storeBean = storeService.findByStoreAccountAndStorePassword(storeAccount, storePassword);
		if (storeBean != null) {
			model.addAttribute("storeUser", storeBean);
		} else {
			result.rejectValue("emailOrPasswordError", "", "帳號或密碼錯誤");
			return "Member/login";
		}
		// 給cookie
		processCookies(storeAccount, storePassword, request, response);
		return "redirect:/store/storeIndex";

	}

	// 登出帳號
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response,
			SessionStatus status) {
		if (model.getAttribute("storeUser") != null) {
			status.setComplete(); // 移除@SessionAttributes({"storeUser"}) 標示的屬性物件
			session.invalidate(); // session.invalidate()讓SESSION失效.
		}

		if (checkCookie(request, model)) {
			deleteCookie(request, response);
		}
		return "redirect:/";
	}

	// 確認密碼是否依樣
	public boolean comfirmPassword(StoreBean storeBean) {
		if (storeBean.getStorePassword().equals(storeBean.getStoreCheckPassword())) {
			return false;
		}
		return true;

	}

	// 給登入商家cookie
	private void processCookies(String storeAccount, String storePassword, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookieAccount = null;
		Cookie cookiePassword = null;

		cookieAccount = new Cookie("account", storeAccount);
		cookieAccount.setMaxAge(6 * 60 * 60); // Cookie的存活期: 6小時
		cookieAccount.setPath(request.getContextPath()); // cookie設置路徑(抓到首頁NeverStarve前)

//		String encodePassword = GlobalService.encryptString(password);
		cookiePassword = new Cookie("password", storePassword);
		cookiePassword.setMaxAge(6 * 60 * 60); // Cookie的存活期: 6小時
		cookiePassword.setPath(request.getContextPath());

		// 給前段創立cookie
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);

	}

	// 刪除cookie
	private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookieAccount = null;
		Cookie cookiePassword = null;

		String account = "";
		String password = "";

		cookieAccount = new Cookie("account", account);
		cookieAccount.setMaxAge(0); // Cookie的存活期: 
		cookieAccount.setPath(request.getContextPath()); // cookie設置路徑(抓到首頁NeverStarve前)

		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge(0); // Cookie的存活期: 
		cookiePassword.setPath(request.getContextPath());

		// 給前段創立cookie
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);

	}

	// 確認有沒有cookie
	public boolean checkCookie(HttpServletRequest request, Model model) {
		Cookie cookies[] = request.getCookies();
		StoreBean storeBean = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("account")) {
					storeBean = storeService.findCookieByStoreAccount(cookie.getValue());
				}
				if (storeBean != null) {
					model.addAttribute("storeUser", storeBean);
					return true;
				}
			}
		}
		return false;
	}


}
