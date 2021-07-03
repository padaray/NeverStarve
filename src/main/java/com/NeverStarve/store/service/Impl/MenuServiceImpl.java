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

	//用cookie找到storeBean再去抓菜單
	@Override
	public List<MenuBean> getMenuByStoreBean(StoreBean storeBean) {
		return menuRepository.findByStoreBean(storeBean);
	}

	//育霆用的
	@Override
	public MenuBean getMenuById(Integer id) {
		return menuRepository.getById(id);
	}

	//育霆用的
	@Override
	public List<MenuBean> getMenuByStroeId(Integer id) {
		StoreBean storeBean = storeRepository.getById(id);
		return menuRepository.findByStoreBean(storeBean);
	}
	
	//用菜品ID去刪除菜品
	@Override
	public void deleteByDishId(Integer dishId) {
		menuRepository.deleteByDishId(dishId);
	}

	@Override
	public void saveMenuList(List<MenuBean> menuListS, StoreBean storeBean) {
		// TODO Auto-generated method stub
		for(MenuBean MLS: menuListS) {
			MLS.setStoreBean(storeBean);
			menuRepository.save(MLS);
		}
	}
	
	

}
