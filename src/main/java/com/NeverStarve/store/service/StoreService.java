package com.NeverStarve.store.service;

import java.util.Optional;

import com.NeverStarve.store.model.StoreBean;

public interface StoreService {
	
	StoreBean save(StoreBean storeBean);
	
	Optional<StoreBean> findoneById(int id);

	boolean accountExist(String storeAccount);
}
