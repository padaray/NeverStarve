package com.NeverStarve.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;
public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

//	List<OrderBean> findBypkOrderId(Integer Id); //why use List??
	
//	List<OrderBean> findBy shopid????
	
//	List<OrderBean> findByOrderDate(Date orderDate );
//	List<OrderBean> findAllBeans();
	
	List<OrderBean> findByStoreBean(StoreBean storebean );
	
}
