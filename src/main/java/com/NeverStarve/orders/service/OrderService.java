package com.NeverStarve.orders.service;

import java.util.List;
import java.util.Optional;

import com.NeverStarve.orders.model.OrderBean;

public interface OrderService {
	
	 Optional<OrderBean> findByPkOrderId(int pkOrderId);
	
	OrderBean save(OrderBean orderBean);
	
	List<OrderBean> findll();

}