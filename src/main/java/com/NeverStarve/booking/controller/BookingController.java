package com.NeverStarve.booking.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.service.BookingService;

@Controller
@RequestMapping("/Booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;

	@GetMapping("/")
	public String toBookingTable() {
		
		return "booking/bookingTable";
	}
	
	@PostMapping("/bookingPost")
	public String postBooking(BookingTableBean btb) {
		
		btb.setPostTime(new Date());
		
		bookingService.save(btb);
				
		return "booking/bookingConfirm";
	}
}
