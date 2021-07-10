package com.NeverStarve.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.store.model.StoreBean;
public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

List<OrderBean> findByStoreBean(StoreBean storebean);

List<OrderBean> findOrdersByMemberBean(MemberBean memberBean);


	
}
