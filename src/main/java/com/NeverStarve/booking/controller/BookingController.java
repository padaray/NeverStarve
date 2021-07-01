package com.NeverStarve.booking.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.member.model.MemberBean;

@Controller
@RequestMapping("/Booking")
@SessionAttributes({"member"})
public class BookingController {
	
	@Autowired
	private BookingService bookingService;

	@GetMapping("/")
	public String toBookingTable(Model model, SessionStatus status) {
		
		MemberBean memberBean = (MemberBean) model.getAttribute("member");
		if (memberBean == null) {
			status.setComplete();
			return "redirect:/Member/login";
		}
		
		return "booking/bookingTable";
	}
	
	@PostMapping("/bookingPost")
	public String postBooking(BookingTableBean btb, Model model) {
		
		//取得會員的id傳進預約資料表中
		//BookingTableBean的memberBean的cascadeType需改成cascade.MERGE
		MemberBean memberBean =(MemberBean) model.getAttribute("member"); 
//		Integer memberId = memberBean.getPkMemberId();
		btb.setMemberBean(memberBean);
		
		btb.setPostTime(new Date());
		model.addAttribute("btb", btb);
		
		bookingService.save(btb);
				
		return "booking/bookingConfirm";
	}
}
