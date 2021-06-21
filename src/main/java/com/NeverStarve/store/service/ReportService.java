package com.NeverStarve.store.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.NeverStarve.orders.model.OrderListBean;

public interface ReportService {
	public Integer getpkStoreId(HttpSession session);
	
	public List<OrderListBean> getOrderListAll(HttpSession session);
}
