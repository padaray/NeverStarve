package com.NeverStarve.member.repository;

import java.time.LocalDate;
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
	
	Page<MemberBean> findByAddressContaining(Pageable pageable,String adderss);

	List<MemberBean> findByRegisterTimeBetween(LocalDate start,LocalDate end);
	
	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(LocalDate start,LocalDate end,String address);
	
	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,LocalDate start,LocalDate end,String address);
	
//	List<MemberBean> findByRegisterTimeBetween(Timestamp start,Timestamp end);
//	
//	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Timestamp start,Timestamp end,String address);
//	
//	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,Timestamp start,Timestamp end,String address);
//}
}