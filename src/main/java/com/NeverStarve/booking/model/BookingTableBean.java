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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
	
	@ManyToOne
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;
	
//	String memberName;
	
//	String storeName;
	
	@DateTimeFormat(pattern = "yyyy 年 MM 月 dd 日")
	private Date bookingDate;
	
	@NotNull(message = "用餐時間必選")
	@DateTimeFormat(pattern = "HH:mm")
	private Date bookingTime;
	
	private Integer bookingNum;
	
	private Date postTime; //送出預約的時間紀錄

//	Double	totalAmount;
	
	Integer	cancelTag; //取消訂單: -1; 未取消: 1
	
	
}
