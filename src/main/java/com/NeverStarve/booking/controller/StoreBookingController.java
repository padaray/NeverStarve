package com.NeverStarve.booking.controller;

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
import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.booking.service.StoreBookingService;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@Controller
@RequestMapping("/StoreBooking")
@SessionAttributes({"storeUser", "store"})
public class StoreBookingController {
	
	@Autowired
	private StoreBookingService storeBookingService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping("/")
	public String toBookingTable(Model model, SessionStatus status) {
		
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		if (storeBean == null) {
			status.setComplete();
			return "redirect:/store/login";
		}
		
		return "booking/storebooking";
	}
	
	@GetMapping("/storeBookingByStoreId/{storeId}")
	public String postBookingByStoreId(@PathVariable Integer storeId, BookingTableBean btb, 
													 Model model, SessionStatus status) {
		
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		if (storeBean == null) {
			status.setComplete();
			return "redirect:/store/login";
		}
		
		//由路徑變數的storeId取得該storeBean(測試中)
		StoreBean store = null;
		Optional<StoreBean> opt = storeService.findoneById(storeId);
		if (opt.isPresent()) {
			store = opt.get();
		}else {
			return "redirect:/";
		}
		model.addAttribute("store", store);
		System.out.println(store);
		
		return "booking/storebooking";
	}
	
	@PostMapping("/storeBookingPost")
	public String postBooking(StoreBookingBean sbb, BindingResult result, Model model) {
	
		//取得店家的id傳進預約資料表中
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		sbb.setStoreBean(storeBean);
//		System.out.println(storeBean);
		
//		sbb.setPostTime(new Date());
		model.addAttribute("sbb", sbb);
		
//		System.out.println(sbb.getBookingTimes());
		
		String timeStr = "";
		for(Date time : sbb.getBookingTimes()) {
			timeStr += String.valueOf(time) + ","; 
		}
		timeStr = timeStr.substring(0, timeStr.length()-1);
//		System.out.println("hi: " + timeStr);

		String[] timeStrSplit = timeStr.split(",");
		for(String t : timeStrSplit) {
			StoreBookingBean newBean = new StoreBookingBean();
			newBean.setBookingTime(t);
			newBean.setBookingDate(sbb.getBookingDate());
			newBean.setStoreBean(storeBean);
			
			System.out.println(t);

			storeBookingService.save(newBean);
		}
		
		return "booking/storeBookingConfirm";
	}
	
	@GetMapping("/findBookings/{BookingNo}")
	public String modifyBookingByMemberAndBookingNo(@PathVariable Integer BookingNo, 
														BookingTableBean btb, Model model, SessionStatus status) {
		
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		if (storeBean == null) {
			status.setComplete();
			return "redirect:/store/login";
		}
		
		// 由路徑變數的bookingNo取得該bookingTableBean(測試中)
		BookingTableBean btb1 = null;
		Optional<BookingTableBean> opt = bookingService.findOneById(BookingNo);
		if (opt.isPresent()) {
			btb1 = opt.get();
		} else {
			return "redirect:/booking/storeAllBookings";
		}
		model.addAttribute("modifyBtb", btb1);
		System.out.println(btb1);
		
		return "booking/modifyBookingTable";
	}
	
	//店家查詢所有預約訂單，根據StroeBean
	@GetMapping("/findBookings")
	public String findBookingsByStoreId(Model model, SessionStatus status) {
		
		StoreBean storeBean = (StoreBean) model.getAttribute("storeUser");
		if (storeBean == null) {
			status.setComplete();
			return "redirect:/store/login";
		}

		List<BookingTableBean> bookings = bookingService.getStoreBookings(storeBean);
		model.addAttribute("bookingList", bookings);
//		System.out.println(bookings);
		
//		Date todayDate = new Date();
//		model.addAttribute("todayDate", todayDate);
		
		return "/booking/storeAllBookings";
	}

}
