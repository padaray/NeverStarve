package com.NeverStarve.booking.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.booking.model.BookingTableBean;
import com.NeverStarve.booking.repository.BookingRepository;
import com.NeverStarve.booking.repository.StoreBookingRepository;
import com.NeverStarve.booking.service.BookingService;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.member.service.MemberService;
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
	
	@Autowired
	private MemberService memberService;

//	@Transactional
//	@Override
//	public BookingTableBean save(BookingTableBean btb) {
//		return bookingRepository.save(btb);
//		
//	}
	
//	@Transactional
	@Override
	public int saveB(BookingTableBean btb) {

		//客戶訂位的資訊(btb)
		Date bDate = btb.getBookingDate();
//		System.out.println("hi1: "+ bDate);
		Date bTime = btb.getBookingTime();
//		System.out.println("hi2: "+ bTime);
		StoreBean bStoreBean = btb.getStoreBean();
//		System.out.println("hi3: "+ bStoreBean);
		Integer bNum = btb.getBookingNum();
//		System.out.println("hi4: "+ bNum);
		
//		Integer bStoreId = bStoreBean.getPkStoreId();
//		System.out.println("hi5: "+ bStoreId);
//		StoreBean originStoreBean = null;
//		Optional<StoreBean> opt = storeRepository.findById(bStoreId);
//		if(opt.isPresent()) {
//			originStoreBean = opt.get();
//			System.out.println("hi6: "+ opt);
//		}
//		System.out.println("hi7: "+ originStoreBean);
		
		Integer totalSeatNum = bStoreBean.getSeatNumber();
//		System.out.println("hi8: "+ totalSeatNum);
		List<BookingTableBean> bookedNumBeans = 
				bookingRepository.findBookingNumByBookingDateAndBookingTimeAndStoreBean(bDate, bTime, bStoreBean);
//		System.out.println("hi9: "+ bookedNumBeans);
		
		Integer bookedNoSum = 0;
		System.out.println("hi10A: "+bookedNumBeans.size());
		for(BookingTableBean bookedNumBean : bookedNumBeans) {
			if(bookedNumBean.getCancelTag() == 1) {
				Integer bookedNum = bookedNumBean.getBookingNum();
				bookedNoSum += bookedNum;
				
				System.out.println("hi10: "+ bookedNum);
			}
		}
		System.out.println("hi11: "+ totalSeatNum);
		System.out.println("hi12: "+ bookedNoSum);
		//剩餘座位數 = 店家設定總座位數 - 已預約座位數總和
		int remainSeat = totalSeatNum - bookedNoSum;
		try {
			System.out.println("testA: 剩餘座位足夠" + ", 預約人數: " + bNum + ", 剩餘座位: " + remainSeat);
			if (bNum <= remainSeat) {
				System.out.println("testB: 剩餘座位足夠" + ", 預約人數: " + bNum + ", 剩餘座位: " + remainSeat);
				System.out.println("test: 剩餘座位足夠");
				
				bookingRepository.save(btb);
//				System.out.println("hi12: "+ bookingRepository.save(btb));

				//1. to; 2. subject; 3. content
				memberService.sendSimpleMail(btb.getMemberBean().getEmail(), 
											"[NeverStarve通知] 訂位成功囉!", 
											bookingSuccessContent(btb));
				
				return 1;
				
			} else {
				System.out.println("test: 剩餘座位不足" + ", 預約人數: " + bNum + ", 剩餘座位: " + remainSeat);
				System.out.println("test: 剩餘座位不足");
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public String bookingSuccessContent(BookingTableBean btb) {
	
		String memberName = btb.getMemberBean().getName();
		String storeName = btb.getStoreBean().getStoreName();
		String stroePhone = btb.getStoreBean().getStorePhone();
		Date bookDate = btb.getBookingDate();
		Date bookTime = btb.getBookingTime();
		Integer bookNum = btb.getBookingNum();

		//SimpleDateFormat("yyyy 年 MM 月 dd 日 (E)", Locale.ENGLISH); E是星期
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy 年 MM 月 dd 日 (E)");
		String bookDateFormat = sdfDate.format(bookDate);
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		String bookTimeFormat = sdfTime.format(bookTime);
		
		String content =  "親愛的 " + memberName + " 您好，\n\n" 
						+ "您已成功預約：" + storeName +  "\n\n"
						+ "預約日期：" + bookDateFormat + "\n"
						+ "預約時間：" + bookTimeFormat + "\n"
						+ "預約人數：" + bookNum + "\n\n"
						+ "若有任何問題，敬請隨時聯繫店家。"
						+ "店家電話：" + stroePhone + "\n\n"
						+ "感謝您使用本平台預約系統\n"
						+ "(此信函為系統發出，請勿直接回覆) \n\n"
						+ "NeverStarve 預約訂位訂餐平台";
		
		return content;
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
