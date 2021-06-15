package com.NeverStarve.member.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Member")
public class RegisterController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public String login() {
		
		
		return "member/login";
	}
	
	@PostMapping("/login")
	public String loginPost(@RequestParam String email,	@RequestParam String password,HttpSession session) {
		MemberBean member =  memberService.loginMember(email, password);
		if(member != null) {
			session.setAttribute("member", member);
			return "redirect:/";
		}else {
			
		}
		
		return "member/login";
	}
	
	
	@GetMapping("/register")
	public String register(Model model) {
		
		model.addAttribute("memberBean",new MemberBean());
		
		return "member/register";
	}
	
	
	@PostMapping("/register")
	public String registerPost(@Valid MemberBean memberBean,BindingResult result ) {
		
//		 檢查 memberId是否重複  
		if (memberService.emailExists(memberBean.getEmail())) {
			result.rejectValue("email", "", "信箱已存在，請重新輸入");
		}
		
		
		if(!confirmPassword(memberBean)) {
			result.rejectValue("checkPassword","confirmError" ,"密碼不一致");
		}
		
		if(result.hasErrors()) {
			return"member/register";
		}
		
		LocalDate registerTime =LocalDate.now();	
		memberBean.setAddress(memberBean.getMemberCity()+memberBean.getMemberTown()+memberBean.getAddress());
		memberBean.setRegisterTime(registerTime);
		memberBean.setUserType("1");
		memberService.save(memberBean);
		return "redirect:/Member/login";
	}
	

	
	
	public boolean confirmPassword(MemberBean memberBean) {
		
		if(memberBean.getPassword().equals(memberBean.getCheckPassword())) {
			return true;
		}
		return false;
	}
	
	@PostMapping("logout")
	public String logot(HttpSession session) {
		
		session.removeAttribute("member");
		
		return "member/login";
	}
	
	
}
