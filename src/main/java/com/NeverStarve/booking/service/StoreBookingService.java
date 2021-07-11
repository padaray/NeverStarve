package com.NeverStarve.booking.service;

import java.util.Date;
import java.util.List;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreBookingService {

	void persistBooking(StoreBookingBean sbb);
	
	StoreBookingBean save(StoreBookingBean sbb);
	
//	BookingTableBean getBooking(Integer StoreBookingNo);
	
	void saveStoreBookingTimes(List<StoreBookingBean> storeBookimgTimeList, StoreBean storeBean);

//	List<StoreBookingBean> findTimesByDateAndStoreId(StoreBookingBean sbb);

	List<StoreBookingBean> findTimesByDateAndStoreId(Date d, StoreBean storeId);

//	List<BookingTableBean> getMemberBookings(Integer pkMemberId);
}
