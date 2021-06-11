package com.NeverStarve.orders.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.StoreBean;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<OrderBean> findBypkOrderId(Integer id) {
		return orderRepository.findBypkOrderId(id);
	}

	@Override
	public List<OrderBean> getAll() {
		return orderRepository.findAll();
	}


	@Override
	public List<OrderBean> findBystoreBean(StoreBean storeBean) {
		// TODO Auto-generated method stub
		return orderRepository.findBystoreBean(storeBean);
	}

	@Override
	public OrderBean save(OrderBean orderBean) {
		return orderRepository.save(orderBean);
	}
	
	
	
	
	

}
