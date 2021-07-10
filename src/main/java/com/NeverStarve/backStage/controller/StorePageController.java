package com.NeverStarve.backStage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;
import com.NeverStarve.util.NeverStarveUtil;

@Controller
@RequestMapping("/Backstage/Store")
public class StorePageController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	StoreService storeService;
	
	NeverStarveUtil util = new NeverStarveUtil();
	
	@GetMapping("/ShowStore")
	public String displayPageProducts(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false,defaultValue = "1") Integer pageNo
			,@RequestParam(value = "city", required = false)String city) {

		Map<Integer, StoreBean> StoreMap = storeService.getPageStores(pageNo);
		model.addAttribute("pagecounts",storeService.getTotalcount()); //取得取回來的總筆數
		model.addAttribute("counts",storeService.getRecordCounts()); //取得資料庫共有幾筆
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("recordsPerPage", storeService.getRecordsPerPage());
		model.addAttribute("totalPages", storeService.getTotalPages());
		model.addAttribute("SearchResult","所有店家");
		model.addAttribute("pagecounts",storeService.getTotalcount()); //取得取回來的總筆數
		model.addAttribute("products_DPP", StoreMap);
		return "backstage/StorePag";
	}
	
	@GetMapping("/Showstore/{id}")
	public String showOneStore(Model model, @PathVariable Integer id,HttpServletRequest request ) {
		MemberBean member = memberService.getMamberById(Integer.valueOf(util.getCookieValueByCookieName("userId", request))).get();
		
		if(member.getUserType().equals("0")) {
			model.addAttribute("storeUser",storeService.findoneById(id));
		}
		
		return "store/modifyInfo";
	}

}
