package com.NeverStarve.booking.service;

import java.util.List;

import com.NeverStarve.booking.model.BookingTableBean;

public interface BookingService {

	void persistBooking(BookingTableBean bookingTableBean);
	
	BookingTableBean getBooking(Integer BookingNo);
	
	List<BookingTableBean> getAllBooking();
	
	List<BookingTableBean> getMemberBooking();
}
