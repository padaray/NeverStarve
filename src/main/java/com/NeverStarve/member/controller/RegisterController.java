package com.NeverStarve.member.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Member")
public class RegisterController {
	
	@Autowired
	MemberService memberServicr;
	
	@GetMapping("/login")
	public String login() {
		
		
		return "member/login";
	}
	
	
	
	
	@GetMapping("/register")
	public String register(Model model) {
		
		model.addAttribute("member",new MemberBean());
		
		return "member/register";
	}
	
	
	@PostMapping("/register")
	public String registerPost(@Valid MemberBean memberBean ,BindingResult result ,Model model) {
		
		// 檢查 memberId是否重複
		if (memberServicr.emailExists(memberBean.getEmail())) {
			result.rejectValue("memberId", "", "帳號已存在，請重新輸入");
		}
		
		
		if(!confirmPassword(memberBean)) {
			result.rejectValue("checkPassword","confirmError" ,"密碼不一致");
		}
		
		if(result.hasErrors()) {
			return"register";
		}
		
		Timestamp registerTime = new Timestamp(System.currentTimeMillis());
		memberBean.setRegisterTime(registerTime);
		memberBean.setUserType("1");
		memberServicr.save(memberBean);
		return "redirect:/member/login";
	}
	

	
	
	public boolean confirmPassword(MemberBean user) {
		
		if(user.getPassword().equals(user.getCheckPassword())) {
			return true;
		}
		return false;
	}
	
	
	
}
