package com.NeverStarve.backStage.service;

import java.util.Collection;
import java.util.List;

import com.NeverStarve.backStage.model.Month;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.orders.model.OrderBean;

public interface BackstageMemberSevice {
	
	List<MemberBean> findByEmailContaining(String email);
	
	Collection<OrderBean> getOrders(MemberBean bean);

	Month countMonthsMember(String yyyymm);
	
	Month countOrder(String yyyy);

	Month sumOrderMany(String yyyy);
	
	
}
