package com.NeverStarve.store.service;

import java.util.Map;

import com.NeverStarve.store.model.StoreBean;

public interface StoreOrderListService {
	
	Map<String, Integer> getOrderListByStoreBean(StoreBean storeBean);
	
}
