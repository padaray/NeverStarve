package com.NeverStarve.backStage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.backStage.service.BackstageMemberSevice;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@RestController
@RequestMapping("/Backstage/Member")
public class RestMemberController {
	@Autowired
	BackstageMemberSevice 	backstageMemberSevice ;
	
	@Autowired
	MemberService memberservice;
	
	@GetMapping("/SearchEmail")
	public @ResponseBody List<MemberBean> findByEmailContaining(@RequestParam(value = "email") String email){
		return  backstageMemberSevice.findByEmailContaining(email);
		
	}
	

	
	
}
