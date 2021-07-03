package com.NeverStarve.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderListBean;

public interface OrderListRepository extends JpaRepository<OrderListBean, Integer> {

}
