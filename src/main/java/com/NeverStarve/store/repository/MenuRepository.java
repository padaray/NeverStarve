package com.NeverStarve.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.store.model.MenuBean;

public interface MenuRepository extends JpaRepository<MenuBean, Integer> {

}
