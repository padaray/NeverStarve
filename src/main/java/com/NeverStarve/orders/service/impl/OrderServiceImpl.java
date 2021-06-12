package com.NeverStarve.orders.service.impl;

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
=======
import javax.transaction.Transactional;

>>>>>>> 78d41aa24f7f285c5a851d46fe5d3cde45d50768
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.orders.service.OrderService;
<<<<<<< HEAD
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository OrderDao;

	@Override
	public Optional<OrderBean> getOrderById(int id) {
		// TODO Auto-generated method stub
		return OrderDao.findById(id);
	}

	@Override
	public List<OrderBean> getOrders() {
		// TODO Auto-generated method stub
		return OrderDao.findAll();
	}

	@Override
	public OrderBean save(OrderBean bean) {
		// TODO Auto-generated method stub
		return OrderDao.save(bean);
	}

	@Override
	public OrderBean updateOrder(OrderBean bean) {
		// TODO Auto-generated method stub
		return OrderDao.save(bean);
	}

	@Override
	public OrderBean queryOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrderByPrimaryKey(int id) {
		OrderDao.deleteById(id);

	}
=======

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
	
	

	
	
	
	
	
>>>>>>> 78d41aa24f7f285c5a851d46fe5d3cde45d50768

}
