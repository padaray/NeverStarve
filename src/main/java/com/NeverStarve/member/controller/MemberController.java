package com.NeverStarve.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Member")
public class MemberController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
}
