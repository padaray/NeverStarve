package com.NeverStarve.backStage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NeverStarve.backStage.model.Month;
import com.NeverStarve.backStage.service.BackstageMemberSevice;

@Controller
@RequestMapping("/Backstage")
public class BackstageReport {
	
	@Autowired
	BackstageMemberSevice backstageMemberservice;
	
	@GetMapping("/Report")
	public String getCount(@RequestParam(value = "date", required = false) String yyyymm, Model model) {

		Month month=backstageMemberservice.countMonthsMember(yyyymm);
		Month order=backstageMemberservice.countOrder(yyyymm);
		Month many=backstageMemberservice.sumOrderMany(yyyymm);
		model.addAttribute("yyyy",yyyymm);
		model.addAttribute("momth",month);
		model.addAttribute("order",order);
		model.addAttribute("many",many);
		return "backstage/Report";
	}
	
	
	
	
	
	
	
	
	

}
