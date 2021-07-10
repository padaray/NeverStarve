package com.NeverStarve.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.booking.repository.StoreBookingRepository;
import com.NeverStarve.booking.service.StoreBookingService;
import com.NeverStarve.store.model.StoreBean;

@Service
public class StoreBookingServiceImpl implements StoreBookingService {

	@Autowired
	private StoreBookingRepository storeBookingRepository;
	
//	@Autowired
//	private MemberRepository memberRepository;
	
//	@Autowired
//	private StoreRepository storeRepository;
	
	@Override
	public void persistBooking(StoreBookingBean sbb) {
		//檢查會員的預約時段是否為可預約狀態
				checkBookingAvaialble(sbb);
				//儲存該筆預約訂單
				storeBookingRepository.save(sbb);

	}

	private void checkBookingAvaialble(StoreBookingBean sbb) {
		
	}

	@Override
	public StoreBookingBean save(StoreBookingBean sbb) {
		return storeBookingRepository.save(sbb);
	}

	@Override
	public void saveStoreBookingTimes(List<StoreBookingBean> storeBookimgTimeList, StoreBean storeBean) {

		for(StoreBookingBean stb : storeBookimgTimeList) {
			stb.setStoreBean(storeBean);
			storeBookingRepository.save(stb);
		}
		
	}

}
