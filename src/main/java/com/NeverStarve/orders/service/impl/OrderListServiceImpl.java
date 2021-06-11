package com.NeverStarve.orders.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.service.OrderListService;

@Service
@Transactional
public class OrderListServiceImpl implements OrderListService {
	
	@Autowired
	OrderListService  orderListService;

	@Override
	public OrderListBean save(OrderListBean orderListBean) {
		return orderListService.save(orderListBean);
	}

}
