package com.NeverStarve.store.service;

import java.util.Map;

import com.NeverStarve.store.model.StoreBean;

public interface ReportService {
	
	public Map<String, Integer> getQuantity(StoreBean mystorebean);
	
}
