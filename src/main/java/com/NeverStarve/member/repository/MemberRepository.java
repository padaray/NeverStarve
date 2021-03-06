package com.NeverStarve.member.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.NeverStarve.member.model.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer>,JpaSpecificationExecutor<MemberBean>,PagingAndSortingRepository<MemberBean, Integer> {
	
	List<MemberBean> findByAddressContaining(String address); 					//限定城市尋找會員 (無分頁)
	
	List<MemberBean> findByEmail(String email);									//驗證帳號有無重複 依照Email找尋會員						
	
	List<MemberBean> findByRegisterTimeBetween(LocalDate start, LocalDate end);	//時間內註冊的所有會員 (無分頁)
	
	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(LocalDate start, LocalDate end, String address); //搜尋時間內註冊 和 限定城市會員 (無分頁)

	Page<MemberBean> findByAddressContaining(Pageable pageable, String adderss); 					//搜尋地區會員 (有分頁)
	
	Page<MemberBean> findByRegisterTimeBetween(Pageable pageable, LocalDate start, LocalDate end); 	//搜尋時間內註冊會員 (有分頁)
	
	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable, LocalDate start, LocalDate end, String address); //搜尋時間內註冊 和 限定城市會員 (有分頁)
	
	MemberBean findByEmailAndPassword(String email,String password);
	
	Long countByAddressContaining(String address);    //回傳有幾筆數量

	Long countByEmailContaining(String email);    //回傳有幾筆數量
	
    @Query(value = "select count(*) from MEMBER t where  t.registerTime like :date% ",nativeQuery = true)
	Long countRegisterTime(@Param("date") String yyyymm); 
	
	List<MemberBean> findByEmailContaining(String email);	//模糊搜尋會員信箱
	
	MemberBean findCookieByEmail(String email);
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value = "UPDATE MEMBER SET password =?1 WHERE email =?2")
	void updateForgotPassword(String password,String email );
	
//	List<MemberBean> findByRegisterTimeBetween(Timestamp start,Timestamp end);
//	
//	List<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Timestamp start,Timestamp end,String address);
//	
//	Page<MemberBean> findByRegisterTimeBetweenAndAddressContaining(Pageable pageable,Timestamp start,Timestamp end,String address);
//}
}