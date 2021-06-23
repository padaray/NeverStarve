package com.NeverStarve.store.service;

import java.util.List;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;

public interface MenuService {
	
	MenuBean save(MenuBean menuBean);
	
	List<MenuBean> getMenuByStoreBean(StoreBean storeBean);
}
