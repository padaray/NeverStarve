package com.NeverStarve.store.service.Impl;
//check it is from store.repository.StoreOrderRepository
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreOrderRepository; //check it is from store.repository.StoreOrderRepository
import com.NeverStarve.store.service.StoreOrderService;
@Service
@Transactional
public class StoreOrderServiceImpl implements StoreOrderService {
	
	@Autowired 
	StoreOrderRepository orderRepository;
	
	@Override
	public List<OrderBean> getOrderByStoreBean(StoreBean storeBean) {
		
		return orderRepository.findByStoreBean(storeBean);
	}

}
