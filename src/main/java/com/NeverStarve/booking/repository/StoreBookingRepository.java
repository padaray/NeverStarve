package com.NeverStarve.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreBookingRepository extends JpaRepository<StoreBookingBean, Integer>,
										   JpaSpecificationExecutor<StoreBookingBean>,
										   PagingAndSortingRepository<StoreBookingBean, Integer>
{

//	List<StoreBookingBean> findTimesByBookingDate(StoreBookingBean sbb);

	List<StoreBookingBean> findTimesByBookingDateAndStoreBean(Date d, StoreBean storeId);

	

	
}