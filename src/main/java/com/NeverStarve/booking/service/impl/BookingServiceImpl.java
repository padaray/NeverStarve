package com.NeverStarve.booking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.repository.BookingRepository;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	
	@Transactional
	@Override
	public void persistBooking(BookingTableBean btb) {
		//檢查會員的預約時段是否為可預約狀態
		checkBookingAvaialble(btb);
		//儲存該筆預約訂單
		bookingRepository.save(btb);

	}

	private void checkBookingAvaialble(BookingTableBean btb) {

		
	}

	@Transactional
	@Override
	public BookingTableBean save(BookingTableBean btb) {
		return bookingRepository.save(btb);
		
	}

	@Transactional
	@Override
	public BookingTableBean getBooking(Integer BookingNo) {
		Optional<BookingTableBean> opt = bookingRepository.findById(BookingNo);
		BookingTableBean bookingTableBean;
		if(opt.isPresent()) {
			bookingTableBean = opt.get();
		}else {
			bookingTableBean = new BookingTableBean();
		}
		return bookingTableBean;
	}

	@Transactional
	@Override
	public List<BookingTableBean> getAllBookings() {
		List<BookingTableBean> list = bookingRepository.findAll(); 
		return list;
	}

	@Transactional
	@Override
	public List<BookingTableBean> getMemberBookings(MemberBean memberBean) {
		List<BookingTableBean> list = bookingRepository.findByMemberBean(memberBean);
		//降冪排序所有訂單
		List<BookingTableBean> decsList = new ArrayList<BookingTableBean>();
		for (int i = list.size()-1; i >= 0; i--) {
			BookingTableBean decsBookings = list.get(i);
			decsList.add(decsBookings);
		}
		return decsList;
	}

	@Override
	public List<BookingTableBean> getStoreBookings(StoreBean storeBean) {
		List<BookingTableBean> list = bookingRepository.findByStoreBean(storeBean);
		//降冪排序所有訂單
		List<BookingTableBean> decsList = new ArrayList<BookingTableBean>();
		for (int i = list.size()-1; i >= 0; i--) {
			BookingTableBean decsBookings = list.get(i);
			decsList.add(decsBookings);
		}
		return decsList;
	}

	@Override
	public Optional<BookingTableBean> findOneById(Integer bookingNo) {
		return bookingRepository.findById(bookingNo);
	}
}
