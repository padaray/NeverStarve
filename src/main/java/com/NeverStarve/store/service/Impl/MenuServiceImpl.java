package com.NeverStarve.store.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.MenuRepository;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuRepository menuRepository;
	@Autowired
	StoreRepository storeRepository;

	@Override
	public MenuBean save(MenuBean menuBean) {
		return menuRepository.save(menuBean);
	}

	@Override
	public List<MenuBean> getMenuByStoreBean(StoreBean storeBean) {
//		StoreBean storeBean = storeRepository.getById(id);
		return menuRepository.findByStoreBean(storeBean);
	}

}
