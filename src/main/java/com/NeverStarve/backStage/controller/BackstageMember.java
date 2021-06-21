package com.NeverStarve.backStage.controller;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.response.MemberResponse;
import com.NeverStarve.member.service.MemberService;

@Controller
@RequestMapping("/Backstage/Member")
public class BackstageMember {
	
	@Autowired
	MemberService memberservice;
	
	@GetMapping("/{id}")
	public String getMamberById(@PathVariable int id, Model model) {
		Optional<MemberBean> member = memberservice.getMamberById(id);
		MemberBean m = member.get();
		model.addAttribute("member",m);
		return "backstage/update" ;
	}
	
//	@PostMapping("/{id}")
//	public String update(@PathVariable int id, Model model,@ModelAttribute("member") @Valid MemberBean memberBean,
//			BindingResult result,RedirectAttributes ra) {
//		
//		if(result.hasErrors()) {
//			return "backstage/update";
//		}
//			
//		memberBean.setAddress(memberBean.getMemberCity()+" "+memberBean.getMemberTown()+" "+memberBean.getAddress());
//		memberBean.setPkMemberId(id);
//		ra.addAttribute("success","更新成功");
//		memberservice.save(memberBean);
//		return "redirect:/Backstage/Member/{id}";
//	
//	}
	
	@PostMapping(value = "/update")
	@ResponseBody
	public MemberResponse updateMember(@ModelAttribute @Valid MemberBean memberBean,
								BindingResult result,@RequestParam(value = "pkid", required = false) Integer pkid) {
		MemberResponse response = new MemberResponse();
		if(result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream().collect(
					Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
		}else {
			response.setValidated(true);			
			memberservice.updateMember(memberBean);
		}
		return response;		
	}
		
	
}
