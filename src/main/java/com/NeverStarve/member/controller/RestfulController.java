package com.NeverStarve.member.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@RestController
@RequestMapping("/Member")
public class RestfulController {

	@Autowired
	MemberService memberservice;

	@GetMapping("/getMamberById/{id}")
	public Optional<MemberBean> getMamberById(@PathVariable int id) {

		return memberservice.getMamberById(id);
	}

	@PostMapping("/save")
	public String save(@RequestBody MemberBean bean) {
		memberservice.save(bean);

		return "redirect:/getAllMembers";

	}

	@GetMapping("/getAllMembers")
	public List<MemberBean> getMembers() {
		return memberservice.getMembers();
	}

	@GetMapping("/findMemberbyemail/{email}")
	public MemberBean queryMember(@PathVariable String email) {
		return memberservice.queryMember(email);
	}

	@GetMapping("/findByRegisterTimeBetween/{begin}/{end}")
	public List<MemberBean> findByRegisterTimeBetween(@PathVariable String begin, @PathVariable String end) {
		return memberservice.findByRegisterTimeBetween(begin, end);
	}

	@GetMapping("/findByAddressLike/{city}")
	public List<MemberBean> findByAddressLike(@PathVariable String city) {
		return memberservice.findByAddressContaining(city);
	}

}
