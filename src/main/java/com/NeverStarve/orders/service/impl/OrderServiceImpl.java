package com.NeverStarve.orders.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.repository.OrderListRepository;
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
	
	@Autowired
	private OrderListRepository orderListRepository;

	@Override
	public OrderBean update(OrderBean orderBean) {
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



	@Override
	public boolean saveOrderBeanAndOrderList(OrderBean orderBean, 
			List<OrderListBean> orderList) {
			try {
				orderRepository.save(orderBean);
				for(OrderListBean ordL :orderList) {
					//跟OrderBean做綁定
					ordL.setOrderBean(orderBean);
					orderListRepository.save(ordL);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
			
	}



	@Override
	public Optional<OrderBean> getNewestOrderByMember(MemberBean memberBean) {
		
		 Set<OrderBean> memberOrder = memberBean.getOrders();
		 LocalDateTime dateTime = null;
		 Optional<OrderBean> orderBean = null;
		 for(OrderBean checkDate:memberOrder) {
			 if(dateTime == null || dateTime.isBefore(checkDate.getOrderDate())) {
				 dateTime = checkDate.getOrderDate();
				 orderBean = Optional.of(checkDate);
			 }
			
		 }
		 System.out.println(orderBean.get().getPkOrderId());
		return orderBean;
	}



	

 

	
	

	

}
