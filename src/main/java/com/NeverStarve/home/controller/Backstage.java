package com.NeverStarve.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.init.MemberInitins;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Backstage")
public class Backstage {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberInitins memberinit;
	@GetMapping("/")
	public String index(Model  model) {	
		Long keeLong=memberService.getCityCount("基隆市");
		Long tip=memberService.getCityCount("台北市");
		Long newtip=memberService.getCityCount("新北市");
		Long Taoyuan=memberService.getCityCount("桃園市");
		Long Taichung=memberService.getCityCount("台中市");
		Long Kaohsiung=memberService.getCityCount("高雄市");
		model.addAttribute("keeLong",keeLong);
		model.addAttribute("tip",tip);
		model.addAttribute("newtip",newtip);
		model.addAttribute("Taoyuan",Taoyuan);
		model.addAttribute("Taichung",Taichung);
		model.addAttribute("Kaohsiung",Kaohsiung);
		
		return "backstage/BackstageIndex";
	}
	@GetMapping("/init")
	public String init(Model model) {
		memberinit.initData();
		return "redirect:/Backstage/";
		
	}
	
}
