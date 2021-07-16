package com.NeverStarve.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

public interface BookingRepository extends JpaRepository<BookingTableBean, Integer>
{

	List<BookingTableBean> findByMemberBean(MemberBean memberBean);

	List<BookingTableBean> findByStoreBean(StoreBean storeBean);

	List<BookingTableBean> findByBookingDateAndBookingTimeAndStoreBean(Date bDate, Date bTime, StoreBean bStoreBean);
	
}