package com.NeverStarve.backStage.service;

import java.util.List;

import com.NeverStarve.member.model.MemberBean;

public interface BackstageMemberSevice {
	
	List<MemberBean> findByEmailContaining(String email);
}
