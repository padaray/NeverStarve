package com.NeverStarve.store.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

public interface StoreService {
	
	StoreBean save(StoreBean storeBean);
	
	Optional<StoreBean> findoneById(int id);
	
	List<StoreBean> findAll();
	
	boolean accountExist(String storeAccount);
	
	StoreBean findByStoreAccountAndStorePassword(String storeAccount, String storePassword);
	
	StoreBean  findCookieByStoreAccount(String storeAccount);
	
	Map<Integer, StoreBean> getPageStores(int pageNo);
	Map<Integer, StoreBean> findByAddressContaining(int pageNo,String adderss);

	int getRecordsPerPage();

	long getRecordCounts();

	int getTotalPages();

	void setRecordsPerPage(int recordsPerPage);

	long getTotalcount();
	
	long getCityCount(String address);
}
