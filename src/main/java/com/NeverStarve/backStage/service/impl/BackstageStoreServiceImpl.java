package com.NeverStarve.backStage.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.backStage.service.BackstageStoreService;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;

@Service
@Transactional
public class BackstageStoreServiceImpl implements BackstageStoreService {

	@Autowired
	StoreRepository storeRepository;

	@Override
	public boolean changeLV(Integer storeId, Integer storeLv) {
		boolean b1;
		try {
			StoreBean StoreBean = storeRepository.findById(storeId).get();
			StoreBean.setStoreLv(storeLv);
			storeRepository.save(StoreBean);
			b1 = true;
		} catch (Exception e) {
			e.printStackTrace();
			b1 = false;
		}

		return b1;
	}

}
