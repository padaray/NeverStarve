package com.NeverStarve.store.service;

import java.util.List;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreOrderService {

	List<OrderBean> getOrderByStoreBean(StoreBean storeBean);
	
	boolean changeConfirm(Integer orderId,Integer confirm);
}
