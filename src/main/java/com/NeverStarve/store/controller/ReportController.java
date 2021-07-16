package com.NeverStarve.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.store.service.ReportService;

 
@Controller
@RequestMapping("/store")
public class ReportController {
	@Autowired ReportService reportService;
	
	@GetMapping("/testpiechart")
	public String testpiechart (Model model) {
		model.addAttribute("chartData", getChartData());
	return "report/piechart";
	}

	private List<List<Object>> getChartData() { 
	        return List.of(
	                List.of("滷肉飯",1),
	                List.of("Onions", 1),
	                List.of("Olives", 1),
	                List.of("Zucchini", 1),
	                List.of("Pepperoni", 1)
	        );
	}
	
	
}