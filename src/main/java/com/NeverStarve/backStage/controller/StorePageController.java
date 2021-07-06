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

import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@RequestMapping("/Backstage/Store")
public class StorePageController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/ShowStore")
	public String displayPageProducts(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pageNo
			,@RequestParam(value = "city", required = false)String city) {

		if (pageNo == null) {
			pageNo = 1;			
		}		
		Map<Integer, StoreBean> StoreMap = storeService.getPageStores(pageNo);
		model.addAttribute("pagecounts",memberService.getTotalcount()); //取得取回來的總筆數
//		model.addAttribute("counts",memberService.getRecordCounts()); //取得資料庫共有幾筆
//		model.addAttribute("pageNo", String.valueOf(pageNo));
//		model.addAttribute("totalPages", memberService.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		model.addAttribute("products_DPP", StoreMap);
//		model.addAttribute("SearchResult","所有店家");
		return "backstage/StorePag";
	}

}
