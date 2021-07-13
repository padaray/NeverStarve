package com.NeverStarve.booking.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@RequestMapping("/Booking")
@SessionAttributes({"member", "store"})
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	StoreService storeService;

	@GetMapping("/")
	public String toBookingTable(Model model, SessionStatus status) {
		
		MemberBean memberBean = (MemberBean) model.getAttribute("member");
		if (memberBean == null) {
			status.setComplete();
			return "redirect:/Member/login";
		}
		
		return "booking/bookingTable";
	}
	
	@GetMapping("/BookingByStoreId/{storeId}")
	public String toBookingTableByStoreId(@PathVariable Integer storeId, BookingTableBean btb, 
													 Model model, SessionStatus status) {
		
		MemberBean memberBean = (MemberBean) model.getAttribute("member");
		if (memberBean == null) {
			status.setComplete();
			return "redirect:/Member/login";
		}
		
		//由路徑變數的storeId取得該storeBean
		StoreBean store = null;
		Optional<StoreBean> opt = storeService.findoneById(storeId);
		if (opt.isPresent()) {
			store = opt.get();
		}else {
			return "redirect:/";
		}
		model.addAttribute("store", store);
		System.out.println(store);
		
		return "booking/bookingTable";
	}
	
	@PostMapping("/bookingPost")
	public String postBooking(BookingTableBean btb, BindingResult result, Model model) {
	
		//取得會員的id傳進預約資料表中
		//BookingTableBean的memberBean的cascadeType需改成cascade.MERGE 或不做級聯操作
		MemberBean memberBean = (MemberBean) model.getAttribute("member"); 
		btb.setMemberBean(memberBean);
		
		//取得店家的id傳進預約資料表中
		StoreBean storeBean = (StoreBean) model.getAttribute("store");
		btb.setStoreBean(storeBean);
//		System.out.println(storeBean);
		
		btb.setPostTime(new Date());
		model.addAttribute("btb", btb);
		
		btb.setCancelTag(1); //取消狀態預設值: 1(未取消); 狀態值: -1(確認取消)
		
		//接service.save()的儲存成功或失敗(true or false)
		
		bookingService.save(btb);
				
		return "booking/bookingConfirm";
	}

//	@GetMapping("/findBookings/{BookingNo}")
//	public String modifyBookingByMemberAndBookingNo(@PathVariable Integer BookingNo, 
//														BookingTableBean btb, Model model, SessionStatus status) {
//		
//		MemberBean memberBean = (MemberBean) model.getAttribute("member");
//		if (memberBean == null) {
//			status.setComplete();
//			return "redirect:/Member/login";
//		}
//		
//		// 由路徑變數的bookingNo取得該bookingTableBean(測試中)
//		BookingTableBean btb1 = null;
//		Optional<BookingTableBean> opt = bookingService.findOneById(BookingNo);
//		if (opt.isPresent()) {
//			btb1 = opt.get();
//		} else {
//			return "redirect:/booking/findBookings";
//		}
//		model.addAttribute("modifyBtb", btb1);
//		System.out.println(btb1);
//		
//		return "booking/modifyBookingTable";
//	}
	
	@GetMapping("/findBookings")
	public String findBookingsByMemberId(Model model, SessionStatus status) {
		
		MemberBean memberBean = (MemberBean) model.getAttribute("member");
		if (memberBean == null) {
			status.setComplete();
			return "redirect:/Member/login";
		}

		List<BookingTableBean> bookings = bookingService.getMemberBookings(memberBean);
		model.addAttribute("bookingList", bookings);
//		System.out.println(bookings);
		
//		Date todayDate = new Date();
//		model.addAttribute("todayDate", todayDate);
		
		return "/booking/allBookings";
	}
}
