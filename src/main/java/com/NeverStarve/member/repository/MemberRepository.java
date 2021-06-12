package com.NeverStarve.member.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.NeverStarve.member.model.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer>,JpaSpecificationExecutor<MemberBean>,PagingAndSortingRepository<MemberBean, Integer> {
	
	List<MemberBean> findByAddressContaining(String address);
	
	List<MemberBean> findByEmail(String email);
	
	List<MemberBean> findByRegisterTimeBetween(Timestamp begin,Timestamp end);
	
	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Timestamp begin,Timestamp end,String address);
	
	Page<MemberBean> findByAddressContaining(Pageable pageable,String adderss);
	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,Timestamp begin,Timestamp end,String address);
}
