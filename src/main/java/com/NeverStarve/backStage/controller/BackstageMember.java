package com.NeverStarve.backStage.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.backStage.service.BackstageMemberSevice;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.response.MemberResponse;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.service.OrderService;

@Controller
@RequestMapping("/Backstage/Member")
public class BackstageMember {
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	BackstageMemberSevice backstageMemberservice;
	
	@Autowired
	OrderService orderService ;
	
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
	
	@PutMapping(value = "/update")
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
		
	@GetMapping("order/{id}")
	public String getOrder(@PathVariable int id, Model model) {
		Optional<MemberBean> member = memberservice.getMamberById(id);
		MemberBean m = member.get();
		Collection<OrderBean> list2 = backstageMemberservice.getOrders(m);
		model.addAttribute("id", m.getPkMemberId());
		model.addAttribute("orderSet",list2);
		return "backstage/MemberOrder" ;
	}
	
	@GetMapping("list/{id}")
	public String getliString (@PathVariable int id , Model model) {
		Optional<OrderBean> order = orderService.findByPkOrderId(id);
		OrderBean o = order.get();
		Set<OrderListBean> list = o.getOrderListBean();
		model.addAttribute("orderList",list);
		return "backstage/OrderList";
		
	}
}
