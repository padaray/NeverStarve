package com.NeverStarve.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;

public interface MenuRepository extends JpaRepository<MenuBean, Integer> {
	
	List<MenuBean> findByStoreBean(StoreBean storeBean);

	@Modifying
	@Query(value = "Delete From MENU Where pkDishId = ?1", nativeQuery = true)
	@Transactional
	void deleteByDishId(int dishId);
	
}
