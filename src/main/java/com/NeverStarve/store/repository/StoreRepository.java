package com.NeverStarve.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.store.model.StoreBean;

public interface StoreRepository extends JpaRepository<StoreBean, Integer> {

}
