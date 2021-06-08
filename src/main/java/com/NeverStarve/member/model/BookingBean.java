package com.NeverStarve.member.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.NeverStarve.store.model.StoreBean;

@Entity
@Table(name="BOOKINGTABLE")
public class BookingBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkBookingNo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_Member_Id")
	private MemberBean memberBean;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;
	
	
	String memberName;
	String storeName;
	Integer bookingNum ;
	Date bookingDate;
	
	public BookingBean() {

	}

}
