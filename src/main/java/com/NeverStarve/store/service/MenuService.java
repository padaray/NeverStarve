package com.NeverStarve.store.service;

import java.util.List;

import com.NeverStarve.store.model.MenuBean;

public interface MenuService {
	
	MenuBean save(MenuBean menuBean);
	
	List<MenuBean> getMenuByStroeId(Integer id);
}
