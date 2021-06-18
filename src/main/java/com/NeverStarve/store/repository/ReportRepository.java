package com.NeverStarve.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.store.model.StoreBean;


public  interface ReportRepository extends JpaRepository<OrderListBean, Integer>{

	StoreBean  findByStoreAccountAndStorePassword(         ,       );
//	findbyshopidanddate
//	要回傳的商品的名字，還有購買的數量
//	要靠甚麼找:要靠店家的ID 還有使用者輸入的月份，還有使用者選擇的數量或是金額
	
	@Modifying
    @Query(value = "UPDATE book SET status = ?1 Where shopid = session.get(storebean.shopid)", nativeQuery = true)
    @Transactional
    int updateByJPQL(int status, int id);
}
