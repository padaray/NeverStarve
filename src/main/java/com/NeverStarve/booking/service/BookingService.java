package com.NeverStarve.booking.service;

import java.util.List;

import com.NeverStarve.booking.model.BookingTableBean;

public interface BookingService {

	void persistBooking(BookingTableBean btb);
	
	BookingTableBean save(BookingTableBean btb);
	
	BookingTableBean getBooking(Integer BookingNo);
	
	List<BookingTableBean> getAllBookings();
	
	List<BookingTableBean> getMemberBookings(Integer pkMemberId);
}
