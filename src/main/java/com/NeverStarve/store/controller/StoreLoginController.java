package com.NeverStarve.store.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
			result.rejectValue("storeCheckPassword","confirmError" ,"密碼不一致");
		}
		//確認validator有沒有抱錯
		if(result.hasErrors()) {
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
	
	//上傳圖片到本地
	public void uploadImage(MultipartFile multipartFile) {

		try {
			// 保存圖片
			File file = new File("C:\\_JSP\\workspace\\NeverStarve2.0\\src\\main\\resources\\static\\images\\" + multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("register");

//		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
//	//抓圖片
//	@RequestMapping(value = "/getPicture/{bookId}", method = RequestMethod.GET)
//	public ResponseEntity<byte[]> getPicture(HttpServletResponse resp, @PathVariable Integer bookId) {
//		String filePath = "/resources/static/images/NoImage.jpg";
//
//		byte[] media = null;
//		HttpHeaders headers = new HttpHeaders();
//		String filename = "";
//		int len = 0;
//		BookBean bean = productService.getProductById(bookId);
//		if (bean != null) {
//			Blob blob = bean.getCoverImage();
//			filename = bean.getFileName();
//			if (blob != null) {
//				try {
//					len = (int) blob.length();
//					media = blob.getBytes(1, len);
//				} catch (SQLException e) {
//					throw new RuntimeException("ProductController的getPicture()發生SQLException: " + e.getMessage());
//				}
//			} else {
//				media = toByteArray(filePath);
//				filename = filePath;
//			}
//		} else {
//			media = toByteArray(filePath);
//			filename = filePath;
//		}
//		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
//		String mimeType = context.getMimeType(filename);
//		MediaType mediaType = MediaType.valueOf(mimeType);
//		System.out.println("mediaType =" + mediaType);
//		headers.setContentType(mediaType);
//		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
//		return responseEntity;
//	}
	
	
	

//	private byte[] toByteArray(String filepath) {
//		byte[] b = null;
//		String realPath = context.getRealPath(filepath);
//		System.out.println(realPath);
//		try {
//			File file = new File(realPath);
//			long size = file.length();
//			b = new byte[(int) size];
//			InputStream fis = context.getResourceAsStream(filepath);
//			fis.read(b);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return b;
	
}
