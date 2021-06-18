package com.NeverStarve.orders.controller;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Report")
public class ReportController {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	@GetMapping("/piechart")
	public String test (Model model) {
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