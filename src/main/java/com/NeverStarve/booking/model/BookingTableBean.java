package com.NeverStarve.booking.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BookingTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTableBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkBookingNo;
	
	@ManyToOne
	@JoinColumn(name = "FK_Member_Id")
	private MemberBean memberBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;
	
//	String memberName;
	
//	String storeName;
	
	@DateTimeFormat(pattern = "yyyy 年 MM 月 dd 日")
	private Date bookingDate;
	
	@DateTimeFormat(pattern = "HH:mm")
	private Date bookingTime;
	
	private Integer bookingNum;
	
	private Date postTime; //送出預約的時間紀錄

//	Double	totalAmount;
	
//	String	cancelTag;
	
	
}
