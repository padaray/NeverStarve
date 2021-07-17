package com.NeverStarve.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.NeverStarve.store.model.StoreBean;

public interface StoreRepository extends JpaRepository<StoreBean, Integer> {

	List<StoreBean> findByStoreAccount(String storeAccount);
	
	StoreBean findByStoreAccountAndStorePassword(String storeAccount, String storePassword);
	
	StoreBean findCookieByStoreAccount(String storeAccount);
		
	Page<StoreBean> findByStoreAddressContaining(Pageable pageable, String address);

	List<StoreBean> findByStoreAddressContaining(String address);
	
	List<StoreBean> findByStoreNameContainingOrStoreAddressContainingOrStoreTypeContaining(String keyword,String keyword1,String keyword2);
	
	Long countByStoreAddressContaining(String address);
	
	@Modifying
	@Query(value = "UPDATE STORE SET storeAccount = ?2, storePassword = ?3, storeName = ?4, storeAddress = ?5, storePhone = ?6, storeType = ?7, seatNumber = ?8 WHERE pkStoreId = ?1", nativeQuery = true)
	@Transactional
	Integer saveNoPic(Integer pkStoreId, String storeAccount, String storePassword, String storeName, String storeAddress, String storePhone, String storeType, Integer seatNumber);
	
	List<StoreBean> findByStoreTypeContaining(String storeType);

}
