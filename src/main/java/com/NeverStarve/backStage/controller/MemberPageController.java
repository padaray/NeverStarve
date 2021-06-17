package com.NeverStarve.backStage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@SessionAttributes({ "pageNo", "products_DPP" })
@RequestMapping("/Backstage/Member")
public class MemberPageController {
	
	@Autowired
	MemberService memberService;

	@GetMapping("/ShowMember")
	public String displayPageProducts(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pageNo
			,@RequestParam(value = "city", required = false)String city) {

		if (pageNo == null) {
			pageNo = 1;			
		}		
		Map<Integer, MemberBean> memberMap = memberService.getPageMembers(pageNo);
		model.addAttribute("pagecounts",memberService.getTotalcount()); //取得取回來的總筆數
		model.addAttribute("counts",memberService.getRecordCounts()); //取得資料庫共有幾筆
		model.addAttribute("pageNo", String.valueOf(pageNo));
		model.addAttribute("totalPages", memberService.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		model.addAttribute("products_DPP", memberMap);
		model.addAttribute("SearchResult","所有會員");
		return "backstage/MemberPagListBoot";
	}

	@GetMapping("/PageCityMember")
	public @ResponseBody Map<Integer, MemberBean> PageCityMember(HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra,
		@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "city", required = false)String city,
		@RequestParam(value = "start", required = false) String start, @RequestParam(value = "end", required = false) String end){
	

		if (pageNo == null) {
			pageNo = 1;
		} 
		return  memberService.getMemberData(pageNo, city , start, end);	
	}	
	
//	@GetMapping("/PageCityMember")
//	public @ResponseBody Map<Integer, MemberBean> PageCityMember(Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra,
//			@RequestParam(value = "pageNo", required = false) Integer pageNo,@RequestParam(value = "city", required = false)String city) {
//
//		if (pageNo == null) {
//			pageNo = 1;
//		}
//		Map<Integer, MemberBean> memberMap;
//		if(city != null) {
//			memberMap = memberService.findByAddressContaining(pageNo, city);			
//			tobase64(memberMap);
//			model.addAttribute("products_DPP", memberMap);
//			model.addAttribute("counts",memberService.getTotalcount());
//			String flush = city+"會員";
//			model.addAttribute("SearchResult",flush);
//		}else {	
//			memberMap = memberService.getPageMembers(pageNo);
//			tobase64(memberMap);
//			model.addAttribute("products_DPP", memberMap);
//			model.addAttribute("counts",memberService.getRecordCounts());
//			model.addAttribute("SearchResult","所有會員");
//			
//
//		}
//		model.addAttribute("pageNo", String.valueOf(pageNo));
//		model.addAttribute("totalPages", memberService.getTotalPages());	// 將讀到的一頁資料放入request物件內，成為它的屬性物件
//		return memberMap;
//	}		
	}