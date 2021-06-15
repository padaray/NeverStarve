package com.NeverStarve.store.service;

import java.util.List;
import java.util.Optional;

import com.NeverStarve.store.model.StoreBean;

public interface StoreService {
	
	StoreBean save(StoreBean storeBean);
	
	Optional<StoreBean> findoneById(int id);
	
	List<StoreBean> findAll();
	
	boolean accountExist(String storeAccount);
	
	StoreBean findByStoreAccountAndStorePassword(String storeAccount, String storePassword);
}
