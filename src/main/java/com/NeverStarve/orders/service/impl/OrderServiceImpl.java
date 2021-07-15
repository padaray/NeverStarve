package com.NeverStarve.orders.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@SuppressWarnings("unchecked")
	@Override
	public Optional<OrderBean> getNewestOrderByMember(MemberBean memberBean) {
		//宣告一個變數去接會員的訂單
		 Set<OrderBean> memberOrder = memberBean.getOrders();
		 LocalDateTime dateTime = null;
		 OrderBean orderBean = null ;
		 Set<OrderListBean> orderList =null;
		 String dishName = "";
		 String dishPrice = "";
		 String dishQuan = "";
		 String itemName ="";
		 //遍歷整個會員的訂單
		 for(OrderBean checkDate:memberOrder) {
			 //假如dateTime為null或dateTime的時間是在訂單的時間之前
			 if(dateTime == null || dateTime.isBefore(checkDate.getOrderDate())) {
				 //訂單的時間設為會員下訂的時間
				 dateTime = checkDate.getOrderDate();
				//把下單的日期放回orderBean
				 orderBean = checkDate;
			 }
		 }		
		 orderList = orderBean.getOrderListBean();				 
		 for( OrderListBean orderitem:orderList) {
			 dishName = orderitem.getMenuBean().getDishName();
			 dishPrice = String.valueOf(orderitem.getMenuBean().getDishPrice());
			 dishQuan  = String.valueOf(orderitem.getQuantity());
			 itemName  += dishName+dishPrice+"元"+"X"+dishQuan+"#";
		 }
		 itemName = itemName.substring(0,itemName.length()-1);
		 System.out.println(itemName);
		 orderBean.setItemName(itemName);
		return Optional.of(orderBean);
	}


	//0708排序用
	@Override
	public List<OrderBean> findOrderByMemberBean(MemberBean memberBean) {
		//拿到會員的資料
		List<OrderBean> orderfindm = orderRepository.findOrdersByMemberBean(memberBean);
		List<OrderBean> ordera = new ArrayList<OrderBean>();
		for(int i=orderfindm.size()-1; i>=0; i--) {
			 OrderBean getorderf = orderfindm.get(i);
			 ordera.add(getorderf);
		}
		return ordera;
	}



	




		


	

	

 

	
	

	

}
