package com.NeverStarve.booking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.member.model.MemberBean;

public interface BookingRepository extends JpaRepository<BookingTableBean, Integer>,
										   JpaSpecificationExecutor<BookingTableBean>,
										   PagingAndSortingRepository<BookingTableBean, Integer>
{

	
//	List<BookingTableBean> findByMemberId(String memberId);
	
//	List<BookingTableBean> findByAddressContaining(String address);
	
//	List<BookingTableBean> findByEmail(String email);
	
//	Page<BookingTableBean> findByAddressContaining(Pageable pageable,String adderss);

//	List<MemberBean> findByRegisterTimeBetween(LocalDate start,LocalDate end);
	
//	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(LocalDate start,LocalDate end,String address);
	
//	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,LocalDate start,LocalDate end,String address);
	
//	List<MemberBean> findByRegisterTimeBetween(Timestamp start,Timestamp end);
//	
//	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Timestamp start,Timestamp end,String address);
//	
//	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,Timestamp start,Timestamp end,String address);
}