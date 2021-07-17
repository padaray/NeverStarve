package com.NeverStarve.booking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.NeverStarve.booking.model.BookingTableBean;
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
//		System.out.println(storeId.getStoreName());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println("hi: " + bookingDate.getStrDate());
		
		String Str = bookingDate.getStrDate();
//		System.out.println(Str);
		Str = Str.replace(" ","");
		Str = Str.replace("年","-");
		Str = Str.replace("月","-");
		Str = Str.replace("日","");
//		System.out.println(Str);
		
		Date d = null;
		try {
			d = sdf.parse(Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		System.out.println(sdf.format(d));
		
		System.out.println(storeBookingService.findTimesByDateAndStoreId(d, storeId));
		List<StoreBookingBean> responseStrList = storeBookingService.findTimesByDateAndStoreId(d, storeId);
		
		return responseStrList;
	}
	
	@PostMapping("/cancelBooking/{bookingNo}")
	public void cancelBooking(@PathVariable Integer bookingNo) {
		
		//以預約單號(bookingNo)取得該筆預約訂單，更新cancelTag的數值，save方法存入該筆訂單(originBtb)即可
		//可以加入判斷式，以登入資料的memberId，判斷該memberId等於該筆訂單的fkMemberId時，才執行動作；否則不動作
		BookingTableBean originBtb = bookingService.findOneById(bookingNo).get();
//		System.out.println(originBtb);
		originBtb.setCancelTag(-1);
		
		bookingService.saveB(originBtb);
	}
	
}
