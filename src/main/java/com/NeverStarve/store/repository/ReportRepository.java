package com.NeverStarve.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.store.model.StoreBean;


public  interface ReportRepository extends JpaRepository<OrderListBean, Integer>{
   @Modifying
   @Query(value = "\r\n"
   		+ "select product_name ,quantity \r\n"
   		+ "from ORDERList \r\n"
   		+ "LEFT OUTER JOIN ORDERS\r\n"
   		+ "on ORDERList.FK_OrderBean_Id=ORDERS.pkOrderId\r\n"
   		+ "where ORDERS.storeId=?1 and ORDERS.orderDate = ?2 ", nativeQuery = true)
   @Transactional
   List<OrderListBean> getquantity(Integer pkStoreId ,LocalDate date );
  
//	StoreBean  findByStoreAccountAndStorePassword(         ,       );
//	findbyshopidanddate

//	要回傳的商品的名字，還有購買的數量

//	要靠甚麼找:要靠店家的ID 還有使用者輸入的月份，還有使用者選擇的數量或是金額
	
//	@Modifying
//    @Query(value = "UPDATE book SET status = ?1 Where shopid = session.get(storebean.shopid)", nativeQuery = true)
//    @Transactional
//    int updateByJPQL(int status, int id);
}
