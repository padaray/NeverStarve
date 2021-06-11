package com.NeverStarve.orders.service;

import java.util.List;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;

public interface OrderService {
	
	List<OrderBean> findBypkOrderId (Integer id);
	
	List<OrderBean> getAll();
	
	List<OrderBean>  findBystoreBean  (StoreBean storeBean);
	
	OrderBean save(OrderBean orderBean);
	

}
