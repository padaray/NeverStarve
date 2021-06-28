package com.NeverStarve.orders.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.repository.MenuRepository;
 

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MenuRepository 	menuRepository;
	

	@Override
	public OrderBean save(OrderBean orderBean) {
		return orderRepository.save(orderBean);
	}



	@Override
	public Optional<OrderBean> findByPkOrderId(int pkOrderId) {
		return orderRepository.findById(pkOrderId);
	}



	@Override
	public List<OrderBean> findAll() {
		return orderRepository.findAll();	
	}



	@Override
	public List<MenuBean> getOrderList(String[] menuIdList) {
		List<MenuBean> menuBeans = new ArrayList<MenuBean>();
		//用ForEatch取出所有ID，用ID取出對應的菜品
		for(String id:menuIdList) {
		MenuBean menuBean = menuRepository.getById(Integer.valueOf(id));
			menuBeans.add(menuBean);
		}
		return menuBeans;
	}

 

	
	

	

}
