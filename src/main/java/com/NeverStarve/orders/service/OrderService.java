package com.NeverStarve.orders.service;

import java.util.List;
import java.util.Optional;



import com.NeverStarve.orders.model.OrderBean;

public interface OrderService {

	Optional<OrderBean> getOrderById(int id);

	List<OrderBean> getOrders();

	OrderBean save(OrderBean bean);

	OrderBean updateOrder(OrderBean bean);

	OrderBean queryOrder(String id);

	void deleteOrderByPrimaryKey(int id);


}
