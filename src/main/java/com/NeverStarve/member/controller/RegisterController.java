package com.NeverStarve.member.controller;

import java.time.LocalDate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.member.model.LoginBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Member")
@SessionAttributes({"member"})  //model同時存入session = session.setAttribute("member", member);
public class RegisterController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public String login(Model model,@CookieValue(value="user", required = false, defaultValue = "") String email,
									@CookieValue(value="password", required = false ,defaultValue = "") String password ) {
		
		LoginBean bean = new LoginBean();
		model.addAttribute("loginBean",bean);
		
		return "member/login";
	}
	
	
	
	
	@PostMapping("/login") 
	public String loginPost(@Valid LoginBean bean,BindingResult result, Model model,
							HttpServletRequest request, HttpServletResponse response) {
		
		
		if (result.hasErrors()) {
			return "member/login";
		}
		
		MemberBean member =  memberService.loginMember(bean.getEmail(),bean.getPassword());
		
		if(member != null) {
//			session.setAttribute("member", member);
			model.addAttribute("member" , member);
		}else {
			// NG, 登入失敗, userid與密碼的組合錯誤，放相關的錯誤訊息到 errorMsgMap 之內
			result.rejectValue("emailOrPasswordError", "", "帳號或密碼錯誤");
			return "member/login";
		}

//		HttpSession session = request.getSession();//找你有沒有sessionID沒有的話就造一個新的給你

//		String nextPath = (String)session.getAttribute("servletPath");
//		if (nextPath == null) {
//			nextPath = "/";
//		}
		processCookies(bean, request, response);
		
		return "redirect:/";
	}
	
	
	private void processCookies(LoginBean bean , HttpServletRequest request , HttpServletResponse response) {
		Cookie cookieEmail = null;
		Cookie cookiePassword = null;
		String userEmail = bean.getEmail();
		String password = bean.getPassword();
		
		cookieEmail = new Cookie("email", userEmail);
		cookieEmail.setMaxAge(2 * 60 * 60);       // Cookie的存活期: 2小時
		cookieEmail.setPath(request.getContextPath());

//		String encodePassword = GlobalService.encryptString(password);
		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge( 2 * 60 * 60);
		cookiePassword.setPath(request.getContextPath());
		
		response.addCookie(cookieEmail);
		response.addCookie(cookiePassword);

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
			System.out.println("註冊失敗");
			return "member/register";
		}
		
		LocalDate registerTime =LocalDate.now();	
		memberBean.setAddress(memberBean.getMemberCity()+" "+memberBean.getMemberTown()+" "+memberBean.getAddress());
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
	
	@PostMapping("/logout")
	public String logot(HttpSession session) {
		
		session.removeAttribute("member");
		
		return "member/login";
	}
	
	
	@GetMapping("/memberDetail")
	public String memberDetail() {
		
		return" member/memberDetail";
		
	}
	
	
	
}
