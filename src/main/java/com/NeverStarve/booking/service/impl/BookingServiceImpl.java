package com.NeverStarve.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.repository.BookingRepository;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.store.repository.StoreRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	BookingService bookingService;
	
	@Override
	public void persistBooking(BookingTableBean btb) {
		//檢查會員的預約時段是否為可預約狀態
		checkBookingAvaialble(btb);
		
		bookingRepository.save(btb);

	}

	private void checkBookingAvaialble(BookingTableBean btb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BookingTableBean getBooking(Integer BookingNo) {
		return null;
	}

	@Override
	public List<BookingTableBean> getAllBooking() {
		return null;
	}

	@Override
	public List<BookingTableBean> getMemberBooking() {
		return null;
	}

}
