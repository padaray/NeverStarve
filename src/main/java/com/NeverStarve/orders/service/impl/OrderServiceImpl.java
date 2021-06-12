package com.NeverStarve.orders.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.orders.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	

	@Override
	public OrderBean save(OrderBean orderBean) {
		return orderRepository.save(orderBean);
	}



	@Override
	public Optional<OrderBean> findByPkOrderId(int pkOrderId) {
		return orderRepository.findById(pkOrderId);
	}



	@Override
	public List<OrderBean> findll() {
		return orderRepository.findAll();
	}
	
	

	
	
	
	
	

}
