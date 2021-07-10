package com.NeverStarve.booking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.NeverStarve.store.model.StoreBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "StoreBooking")
@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreBookingBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkStoreBookingNo;
	
	@ManyToOne
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;
	
	@DateTimeFormat(pattern = "yyyy 年 MM 月 dd 日")
	private Date bookingDate;
	
	@DateTimeFormat(pattern = "HH:mm")
	private String bookingTime;
	
	@Transient
	@DateTimeFormat(pattern = "HH:mm")
	private List<Date> bookingTimes;
	
//	private Integer bookingNum;
	
//	private Date postTime; //送出預約的時間紀錄

//	String	cancelTag;
	
	
}
