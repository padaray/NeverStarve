package com.NeverStarve.store.service.Impl;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.repository.OrderRepository;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.ReportService;
@Service
@Transactional
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
		List<OrderBean> orderBean= orderRepository.findByStoreBean(storeBean);//透過鬍鬚張的storebean找到屬於鬍鬚張的訂單，有很多個訂單所以
		//要用list去存
		List<OrderListBean> orderListBean = new ArrayList<OrderListBean>();
		for( OrderBean var: orderBean  ) { //將每筆訂單的訂單詳細存入orderListBean裡面
			orderListBean.add((OrderListBean) var.getOrderListBean());  
		}
        //透過鬍鬚張的訂單找到鬍鬚張的訂單詳細
		return orderListBean;
	}

	
}
