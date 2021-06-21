package com.NeverStarve.store.service.Impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.ReportService;

public class ReportServiceImpl implements ReportService {
	
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	OrderRepository orderRepository;
	@Override
	public Integer getpkStoreId(HttpSession session) {//回傳對應的店家ID，從session拿到
		
		StoreBean mystorebean=(StoreBean) session.getAttribute("storeUser");
//		mystorebean.getOrder()
		return mystorebean.getPkStoreId();
	}
	
	@Override
	public List<OrderListBean> getOrderListAll(HttpSession session) {
		Integer pkStoreId1=getpkStoreId(session); //透過session 找到對應的鬍鬚張ID
		StoreBean storeBean = storeRepository.getById(pkStoreId1); //透過 鬍鬚張ID找到對應的鬍鬚張的StoreBean
		OrderBean orderBean= (OrderBean) orderRepository.findByStoreBean(storeBean);//透過鬍鬚張的storebean找到屬於鬍鬚張的訂單
		OrderListBean  orderListBean=(OrderListBean) orderBean.getOrderListBean();//透過鬍鬚張的訂單找到鬍鬚張的訂單詳細
		return (List<OrderListBean>) orderListBean;
	}

	
}
