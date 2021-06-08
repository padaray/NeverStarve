package com.NeverStarve.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

}
