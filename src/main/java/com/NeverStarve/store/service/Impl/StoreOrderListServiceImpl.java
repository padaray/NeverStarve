package com.NeverStarve.store.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreOrderListRepository;
import com.NeverStarve.store.repository.StoreOrderRepository;
import com.NeverStarve.store.service.StoreOrderListService;
@Service("StoreOrderListServiceImpl")
@Transactional
public class StoreOrderListServiceImpl implements StoreOrderListService {

	@Autowired
	StoreOrderRepository storeOrderRepository;
	@Autowired
	StoreOrderListRepository storeOrderListRepository;

	@Override
	public Map<String, Integer> getOrderListByStoreBean(StoreBean storeBean) {
		
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		List<OrderBean> orderList = storeOrderRepository.findByStoreBean(storeBean);
		
		for (OrderBean orderbean : orderList) {
		
			System.out.println("###XXX"+orderbean.getPkOrderId());
			
			for (OrderListBean orderListBean : orderbean.getOrderListBean()) {
				System.out.println("###XXX"+orderListBean.getPkOrderListId());
				
				String dishName = orderListBean.getMenuBean().getDishName();
				Integer quantity = orderListBean.getQuantity();
				System.out.println(dishName);
				System.out.println(quantity);
				resultMap.put(dishName, resultMap.getOrDefault(dishName, 0) + quantity);

			}
		}
		resultMap.put("ss",6);
		return resultMap;
	}

}
