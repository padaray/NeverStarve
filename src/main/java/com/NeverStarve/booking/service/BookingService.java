package com.NeverStarve.booking.service;

import java.util.List;
import java.util.Optional;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

public interface BookingService {

	int saveB(BookingTableBean btb);
	
	BookingTableBean getBooking(Integer BookingNo);
	
	List<BookingTableBean> getAllBookings();
	
	List<BookingTableBean> getMemberBookings(MemberBean memberBean);
	
	List<BookingTableBean> getStoreBookings(StoreBean storeBean);

	Optional<BookingTableBean> findOneById(Integer bookingNo);
}
