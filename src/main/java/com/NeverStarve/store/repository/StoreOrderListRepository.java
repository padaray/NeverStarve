package com.NeverStarve.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;


public interface StoreOrderListRepository extends JpaRepository<OrderListBean, Integer> {
	
	List<OrderListBean> findByOrderBean(OrderBean orderbean);
	
	//我想利用orderbean來找orderlistbean的內容，我這裡會卡住的地方會是我覺得OrderBean orderbean 應該要放List才對
	
}
