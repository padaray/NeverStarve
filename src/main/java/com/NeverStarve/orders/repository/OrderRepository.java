package com.NeverStarve.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderBean;
public interface OrderRepository extends JpaRepository<OrderBean, Integer> {


//	List<OrderBean> findBypkOrderId(Integer Id); //why use List??
	
//	List<OrderBean> findBy shopid????
	
//	List<OrderBean> findByOrderDate(Date orderDate );
//	List<OrderBean> findAllBeans();
	
//	List<OrderBean> findByStoreBean(StoreBean storebean );

	
	
	

	
}
