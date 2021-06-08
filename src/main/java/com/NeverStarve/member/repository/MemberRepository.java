package com.NeverStarve.member.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.member.model.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer> {
	
	List<MemberBean> findByAddressContaining(String address);
	
	List<MemberBean> findByEmail(String email);
	
	List<MemberBean> findByRegisterTimeBetween(Timestamp begin,Timestamp end);
}
