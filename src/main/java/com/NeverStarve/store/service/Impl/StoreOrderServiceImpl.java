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

	@Override
	public boolean changeConfirm(Integer orderId, Integer confirm) {
		boolean b1;
		try {
			OrderBean orderBean = orderRepository.findById(orderId).get();
			orderBean.setConfirm(confirm);
			orderRepository.save(orderBean);
			b1 = true;
		} catch (Exception e) {
			e.printStackTrace();
			b1 = false;
		}

		return b1;
	}

}
