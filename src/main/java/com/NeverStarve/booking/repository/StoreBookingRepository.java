package com.NeverStarve.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.NeverStarve.booking.model.StoreBookingBean;

public interface StoreBookingRepository extends JpaRepository<StoreBookingBean, Integer>,
										   JpaSpecificationExecutor<StoreBookingBean>,
										   PagingAndSortingRepository<StoreBookingBean, Integer>
{

	
}