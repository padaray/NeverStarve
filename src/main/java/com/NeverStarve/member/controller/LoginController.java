package com.NeverStarve.member.controller;

import java.util.Date;

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
import org.springframework.web.bind.support.SessionStatus;

import com.NeverStarve.member.model.LoginBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Member")
@SessionAttributes({"member"})  //model同時存入session = session.setAttribute("member", member);
public class LoginController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public String login(Model model,@CookieValue(value="email", required = false, defaultValue = "") String email,
									@CookieValue(value="password", required = false ,defaultValue = "") String password ) {
		
		LoginBean bean = new LoginBean();
		model.addAttribute("loginBean",bean);
		
//		確認登入後不能進入login頁面
		if(checkCookie(email, model)) {
			return "redirect:/" ;
		}
		
		return "member/login";
	}
	
	
	@PostMapping("/login") 
	public String loginPost( @Valid LoginBean bean,BindingResult result, Model model,
							HttpServletRequest request, HttpServletResponse response) {
		
		if (result.hasErrors()) {
			return "member/login";
		}
		
		MemberBean member =  memberService.loginMember(bean.getEmail(),bean.getPassword());
		
		if(member != null) {
			model.addAttribute("member" , member);
//			Date date = null;  //DATE轉字串 轉DATE
//				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String str = sdf.format(new Date());
//				 date=sdf.parse(str);
			member.setLongTime(new Date());
			member.setMemberCity("1");
			member.setMemberTown("1");
			memberService.save(member);
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
		processCookies(member, request, response);
		
		return "redirect:/Member/memberDetail";
	}
	
	
	private void processCookies(MemberBean bean , HttpServletRequest request , HttpServletResponse response) {
		Cookie cookieEmail = null;
		Cookie cookiePassword = null;
		Cookie cookieId = null;
		String userEmail = bean.getEmail();
		String password = bean.getPassword();
		String userId = String.valueOf(bean.getPkMemberId());
		
		cookieEmail = new Cookie("email", userEmail); 
		cookieEmail.setMaxAge(2 * 60 * 60);       // Cookie的存活期: 2小時
		cookieEmail.setPath(request.getContextPath());

//		String encodePassword = GlobalService.encryptString(password);
		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge( 2 * 60 * 60);
		cookiePassword.setPath(request.getContextPath());
		
		cookieId = new Cookie("userId" , userId);
		cookieId.setMaxAge( 2 * 60 * 60);
		cookieId.setPath(request.getContextPath());
		
		response.addCookie(cookieEmail);
		response.addCookie(cookiePassword);
		response.addCookie(cookieId);
	}
	
		private void deleteCookies( HttpServletRequest request , HttpServletResponse response) {
		Cookie cookieEmail = null;
		Cookie cookiePassword = null;
		Cookie cookieId = null;
		String email = "";
		String password = "";
		String userId = "";

		cookieEmail = new Cookie("email", email);
		cookieEmail.setMaxAge(0);
		cookieEmail.setPath(request.getContextPath());

//		String encodePassword = GlobalService.encryptString(password);
		cookiePassword = new Cookie("password", password);
		cookiePassword.setMaxAge(0);
		cookiePassword.setPath(request.getContextPath());
		
		cookieId = new Cookie("userId" , userId);
		cookieId.setMaxAge(0);
		cookieId.setPath(request.getContextPath());
		
		response.addCookie(cookieEmail);
		response.addCookie(cookiePassword);
		response.addCookie(cookieId);
	}
	//確認有沒有cookie
	public boolean checkCookie(String email, Model model){
		if(email != null){
				MemberBean memberBean = memberService.cookieLogin(email);
		 		if(memberBean != null) {
		 			model.addAttribute("member", memberBean);
		 			return true;
		 		}
		 	}
		 return false;
	}
	
	

	
	@GetMapping("/logout")
	public String logout(HttpSession session,HttpServletRequest request ,
			HttpServletResponse response , Model model, SessionStatus status,
			@CookieValue(value="email", required = false, defaultValue = "")String email) {
		if(checkCookie(email, model)) {
			deleteCookies(request, response);
		}
		if(model.getAttribute("member") != null) {
			
			status.setComplete();   // 移除@SessionAttributes({"member"}) 標示的屬性物件
			session.invalidate();	// session.invalidate()讓SESSION失效.
		}
		
		return "redirect:/Member/login";
	}
	
	
//	@GetMapping("/memberDetail")
//	public String memberDetail() {
//		
//		return"member/memberDetail";
//		
//	}
	
	
	
}
