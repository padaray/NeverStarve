package com.NeverStarve.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NeverStarve.member.model.MemberBean;

public interface BookingItemRepository extends JpaRepository<MemberBean, Integer>{

}
