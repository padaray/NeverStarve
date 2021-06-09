package com.NeverStarve.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index(Model  model) {
		model.addAttribute("imgurl", "../static/images/NeverStarvelogo3.png");
		return "index";
	}
}
