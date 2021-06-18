package com.NeverStarve.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderBean;


public  interface ReportRepository extends JpaRepository<OrderBean, Integer>{
	
	
}
