package com.NeverStarve.backStage.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.NeverStarve.orders.model.OrderBean;

public interface ReportOrderRepository extends JpaRepository<OrderBean, Integer> {
	
    @Query(value = "select count(*) from ORDERS t where  t.orderDate like :date% ",nativeQuery = true)
	Long countOrder(@Param("date") String yyyymm); 
    
    @Query(value ="SELECT ISNULL(SUM(totalCost), 0 )   FROM ORDERS WHERE MONTH(orderDate) = MONTH(:date) and  YEAR(orderDate) = YEAR(:date)",nativeQuery = true)
    Long countordermany (@Param("date") Date yyyymm);
    
    @Query(value ="SELECT ISNULL(SUM(totalCost), 0 )   FROM ORDERS WHERE YEAR(orderDate) = YEAR(:date)",nativeQuery = true)
    Long countOrderYearmany (@Param("date") Date yyyymm);
    
    
}
