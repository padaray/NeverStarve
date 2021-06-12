package com.NeverStarve.store.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.StoreService;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreRepository storeRepository;

	@Override
	public StoreBean save(StoreBean storeBean) {
		return storeRepository.save(storeBean) ;
	}

}
