package com.NeverStarve.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@SessionAttributes({"randomNumber"})
public class MailController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	HttpSession session;
	
	@GetMapping("mail")
	public String sendMail() {
		
		memberService.sendSimpleMail("chia50505@gmail.com", "謝謝您訂閱米奇不妙屋", "如有任何資訊都會傳送");
		
		return "/member/login" ;
	}
	
	@GetMapping("Member/forgotPassword")
	public String forgotPassword() {
		
		
		return "/member/forgotPassword";
	}
	
	String randomNumber ;
	
	@PostMapping("Member/forgotPassword")
	public String forgotPasswordPost(@RequestParam String email, Model model , BindingResult result) {
		
		MemberBean memberBean = memberService.cookieLogin(email);
		
//		 檢查 memberId是否存在  
		if (!memberService.emailExists(memberBean.getEmail())) {
			result.rejectValue("email", "", "信箱已存在，請重新輸入");
		}
		
		
		randomNumber = "123456";
		model.addAttribute("randomNumber", randomNumber);
		memberService.sendSimpleMail(email, "NeverStarve重設密碼", memberBean.getName()+"您好，以下是您重設密碼的驗證碼"+"http://localhost:9527/NeverStarve/Member/cheakRandomUrl"+randomNumber);
		return "/index";
	}
		
	
	@GetMapping("Member/cheakRandomUrl")
	public String cheakRandomNumber(@RequestParam String randomNumber ) {
		
		String sessionRandomNumber = (String) session.getAttribute("randomNumber");
		if (sessionRandomNumber == randomNumber) {
			
		}
			
		return null;
	}
	
	
	
	
}
