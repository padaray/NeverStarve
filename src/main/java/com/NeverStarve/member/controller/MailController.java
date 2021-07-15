package com.NeverStarve.member.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.member.model.LoginBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.response.MemberResponse;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.store.service.StoreService;

@Controller
@SessionAttributes({ "randomNumber" })
public class MailController {

	@Autowired
	MemberService memberService;

	@Autowired
	HttpSession session;
	
	@Autowired
	StoreService storeService;

	@GetMapping("mail")
	public String sendMail() {

		memberService.sendSimpleMail("chia50505@gmail.com", "謝謝您訂閱米奇不妙屋", "如有任何資訊都會傳送");

		return "/member/login";
	}

	@GetMapping("Member/forgotPassword")
	public String forgotPassword(Model model) {
		LoginBean loginBean = new LoginBean();

		model.addAttribute("loginBean",loginBean);
		
		return "/member/forgotPassword";
	}

	String randomNumber;

	@PostMapping("Member/forgotPasswordp")
	@ResponseBody
	public MemberResponse forgotPasswordPost(@ModelAttribute @Valid LoginBean bean, BindingResult result) {
		//為了讓密碼notBLANK
		bean.setPassword("123");
		MemberBean memberBean = memberService.cookieLogin(bean.getEmail());
		MemberResponse response = new MemberResponse();
		
		//		 檢查 memberId是否存在  
		if(!bean.getEmail().isBlank() && memberBean == null) {
		
			result.rejectValue("email", "", "信箱不存在，請重新輸入");

		}
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
			System.out.println(errors);
		} else {
			response.setValidated(true);

			memberService.sendForgotPasswordMail(bean.getEmail(), "[NeverStarve通知] 重設密碼通知信", memberBean.getName() 
					+"您好，請點下列網址輸入驗證碼\n"+ "http://localhost:9527/NeverStarve/Member/cheakRandomUrl\n");
		}
		return response;

		
	}
	//傳到首頁但包含修改密碼的功能
	@GetMapping("Member/cheakRandomUrl")
	public String cheakRandomNumber(Model model) {
		
		model.addAttribute("checkCode",true);
		
//		//搜尋所有店家資料，用來呈現到首頁上
//		List<StoreBean> storeAll = storeService.findAll();
//		model.addAttribute("stores",storeAll);
		
		return "forward:/";
		
	}
	
	
	@PostMapping("updateOnlyPassword")
	@ResponseBody
	public void cheakRandomNumberPost(@RequestParam String password,@RequestParam String email) {
		
		memberService.updateForgotPassword(password, email);
		System.out.println(password+"email"+email);

	}
	

}
