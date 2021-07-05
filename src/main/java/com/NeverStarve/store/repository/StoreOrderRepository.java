package com.NeverStarve.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreOrderRepository extends JpaRepository<OrderBean, Integer>{
	 
	List<OrderBean> findByStoreBean(StoreBean storeBean);
	//右邊是一個店家，左邊是很多個訂單
//	findByStoreBean(StoreBean storeBean);
	
}
