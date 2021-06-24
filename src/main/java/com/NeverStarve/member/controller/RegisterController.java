package com.NeverStarve.member.controller;

import java.io.File;
import java.sql.Blob;
import java.time.LocalDate;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Member")
//@SessionAttributes({"member"})  //model同時存入session = session.setAttribute("member", member);
public class RegisterController {

	@Autowired
	MemberService memberService;

	@Autowired
	ServletContext context;

	@GetMapping("/register")
	public String register(@CookieValue(value = "email", required = false, defaultValue = "") String email,
			Model model) {

		// 確認登入後不能進入註冊頁面
		if (checkCookie(email, model)) {
			return "redirect:/";
		}

		model.addAttribute("memberBean", new MemberBean());

		return "member/register";
	}

	@PostMapping("/register")
	public String registerPost(@Valid MemberBean memberBean, BindingResult result ) {

//		 檢查 memberId是否重複  
		if (memberService.emailExists(memberBean.getEmail())) {
			result.rejectValue("email", "", "信箱已存在，請重新輸入");
		}

		if (!confirmPassword(memberBean)) {
			result.rejectValue("checkPassword", "confirmError", "密碼不一致");
		}
		if (result.hasErrors()) {
			System.out.println("註冊失敗");
			return "member/register";
		}

		MultipartFile memberImage = memberBean.getMemberImage();
		if (memberImage != null && !memberImage.isEmpty()) {
			String ImageFileName = memberImage.getOriginalFilename();
			memberBean.setFileName(ImageFileName);
			try {
				byte[] b = memberImage.getBytes();
				Blob blob = new SerialBlob(b);
				memberBean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}

		LocalDate registerTime = LocalDate.now();
		memberBean.setAddress(
				memberBean.getMemberCity() + " " + memberBean.getMemberTown() + " " + memberBean.getAddress());
		memberBean.setRegisterTime(registerTime);
		memberBean.setUserType("1");
		memberService.save(memberBean);
		return "redirect:/Member/login";

	}

	public boolean confirmPassword(MemberBean memberBean) {

		if (memberBean.getPassword().equals(memberBean.getCheckPassword())) {
			return true;
		}
		return false;
	}

	@GetMapping("/memberDetail")
	public String memberDetail() {

		return "member/memberDetail";

	}

//	//確認有沒有cookie
	public boolean checkCookie(String email, Model model) {
		if (email != null) {
			MemberBean memberBean = memberService.cookieLogin(email);
			if (memberBean != null) {
				model.addAttribute("member", memberBean);
				return true;
			}
		}
		return false;
	}

}
