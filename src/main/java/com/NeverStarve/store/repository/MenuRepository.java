package com.NeverStarve.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;

public interface MenuRepository extends JpaRepository<MenuBean, Integer> {
	
	
	List<MenuBean> findByStoreBean(StoreBean storeBean);
	
	
}
