package com.NeverStarve.orders.service;

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD


import com.NeverStarve.orders.model.OrderBean;

public interface OrderService {

	Optional<OrderBean> getOrderById(int id);

	List<OrderBean> getOrders();

	OrderBean save(OrderBean bean);

	OrderBean updateOrder(OrderBean bean);

	OrderBean queryOrder(String id);

	void deleteOrderByPrimaryKey(int id);

=======
import com.NeverStarve.orders.model.OrderBean;

public interface OrderService {
	
	 Optional<OrderBean> findByPkOrderId(int pkOrderId);
	
	OrderBean save(OrderBean orderBean);
	
	List<OrderBean> findll();
>>>>>>> 78d41aa24f7f285c5a851d46fe5d3cde45d50768

}
