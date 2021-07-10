package com.NeverStarve.booking.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.booking.service.StoreBookingService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.StoreService;

@RestController
@RequestMapping("/StoreBooking")
public class StoreBookingRestController {

	@Autowired
	StoreService storeService;
	
	@Autowired
	StoreBookingService storeBookingService;
	
	@PostMapping(value="/saveStoreBookingTimes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveStoreBookingTimes(@RequestBody List<StoreBookingBean> storeBookimgTimeList, HttpServletRequest request) {
		StoreBean storeBean = checkCookie(request);
//		StoreBean storeBean = null;
		storeBookingService.saveStoreBookingTimes(storeBookimgTimeList, storeBean);
	}
	
	//確認有沒有cookie
	public StoreBean checkCookie(HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				StoreBean storeBean = storeService.findCookieByStoreAccount(cookie.getValue());
				if (storeBean != null) {
					return storeBean;
				}
			}
		}
		return null;
	}
}
