package com.NeverStarve.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.store.model.StoreBean;

public interface StoreRepository extends JpaRepository<StoreBean, Integer> {

	List<StoreBean> findByStoreAccount(String storeAccount);
	
	StoreBean  findByStoreAccountAndStorePassword(String storeAccount, String storePassword);
}
