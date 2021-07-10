package com.NeverStarve.orders.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.store.model.MenuBean;

public interface OrderService {
	
	Optional<OrderBean> findByPkOrderId(int pkOrderId);
	
	OrderBean update(OrderBean orderBean);
	
	List<OrderBean> findAll();
	
	List<MenuBean> getOrderList(String[] menuIdList);
	
	boolean saveOrderBeanAndOrderList(OrderBean orderBean,List<OrderListBean> orderList);
	
	Optional<OrderBean> getNewestOrderByMember(MemberBean memberBean);
	
	List<OrderBean> findOrderByMemberBean(MemberBean memberBean);
	
	
	
}
