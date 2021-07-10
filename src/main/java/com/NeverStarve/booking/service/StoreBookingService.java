package com.NeverStarve.booking.service;

import java.util.List;

import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreBookingService {

	void persistBooking(StoreBookingBean sbb);
	
	StoreBookingBean save(StoreBookingBean sbb);
	
//	BookingTableBean getBooking(Integer StoreBookingNo);
	
	void saveStoreBookingTimes(List<StoreBookingBean> storeBookimgTimeList, StoreBean storeBean);
	
//	List<BookingTableBean> getMemberBookings(Integer pkMemberId);
}
