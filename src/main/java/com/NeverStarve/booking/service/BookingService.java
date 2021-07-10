package com.NeverStarve.booking.service;

import java.util.List;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.member.model.MemberBean;

public interface BookingService {

	void persistBooking(BookingTableBean btb);
	
	BookingTableBean save(BookingTableBean btb);
	
	BookingTableBean getBooking(Integer BookingNo);
	
	List<BookingTableBean> getAllBookings();
	
	List<BookingTableBean> getMemberBookings(MemberBean memberBean);
}
