package com.NeverStarve.booking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.model.StoreBookingBean;
import com.NeverStarve.booking.repository.BookingRepository;
import com.NeverStarve.booking.repository.StoreBookingRepository;
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
	
	@Autowired
	private StoreBookingRepository storeBookingRepository;
	
	
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

//	@Transactional
//	@Override
//	public BookingTableBean save(BookingTableBean btb) {
//		return bookingRepository.save(btb);
//		
//	}
	
	@Transactional
	@Override
	public boolean save(BookingTableBean btb) {

		//客戶訂位的資訊(btb)
		Date bDate = btb.getBookingDate();
		System.out.println("hi1: "+ bDate);
		Date bTime = btb.getBookingTime();
		System.out.println("hi2: "+ bTime);
		StoreBean bStoreBean = btb.getStoreBean();
		System.out.println("hi3: "+ bStoreBean);
		Integer bNum = btb.getBookingNum();
		System.out.println("hi4: "+ bNum);
		
//		List<StoreBookingBean> storeTimes = storeBookingRepository.findTimesByBookingDateAndStoreBean(bDate, bStoreBean);
		
		Integer bStoreId = bStoreBean.getPkStoreId();
		System.out.println("hi5: "+ bStoreId);
		StoreBean originStoreBean = null;
		Optional<StoreBean> opt = storeRepository.findById(bStoreId);
		if(opt.isPresent()) {
			originStoreBean = opt.get();
			System.out.println("hi6: "+ opt);
		}
		System.out.println("hi7: "+ originStoreBean);
		
		Integer totalSeatNum = originStoreBean.getSeatNumber();
		System.out.println("hi8: "+ totalSeatNum);
		List<BookingTableBean> bookedNumBeans = bookingRepository.findBookingNumByBookingDateAndBookingTimeAndStoreBean(bDate, bTime, bStoreBean);
		System.out.println("hi9: "+ bookedNumBeans);
		
		Integer BookedNoSum = 0;
		for(BookingTableBean bookedNumBean : bookedNumBeans) {
			Integer bookedNum = bookedNumBean.getBookingNum();
			BookedNoSum += bookedNum;
		}
		System.out.println("hi10: "+ BookedNoSum);
		
		try {
			if (bNum < totalSeatNum - BookedNoSum) {
				System.out.println("test: 剩餘座位足夠");
				bookingRepository.save(btb);
				return true;
			} else {
				System.out.println("test: 剩餘座位不足");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
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
