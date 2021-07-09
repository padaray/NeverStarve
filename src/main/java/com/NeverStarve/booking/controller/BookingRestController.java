package com.NeverStarve.booking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.booking.model.StrDateBean;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.booking.service.StoreBookingService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@RestController
@RequestMapping("/Booking")
@SessionAttributes({"member", "store"})
public class BookingRestController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	StoreBookingService storeBookingService;

	@GetMapping(value = "/findTimesByDateAndStoreId/{StoreId}", 
				consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<StoreBookingBean> findTimesByDateAndStoreId(@PathVariable Integer StoreId, StrDateBean bookingDate) {
		
		StoreBean storeId = storeService.findoneById(StoreId).get();
		System.out.println(storeId.getStoreName());
		
//		StoreBean storeBean = (StoreBean) model.getAttribute("store");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println("hi: " + bookingDate.getTestDate());
		
		String Str = bookingDate.getStrDate();
		Str = Str.replace(" ","");
		Str = Str.replace("年","-");
		Str = Str.replace("月","-");
		Str = Str.replace("日","");
		System.out.println(Str);
		
		Date d = null;
		try {
			d = sdf.parse(Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(sdf.format(d));
//		sbb.setStoreBean(storeBean);
//		sbb.setBookingDate(bookingDate);
		
		System.out.println(storeBookingService.findTimesByDateAndStoreId(d, storeId));
		
		return storeBookingService.findTimesByDateAndStoreId(d, storeId);
		
	}
	
}
