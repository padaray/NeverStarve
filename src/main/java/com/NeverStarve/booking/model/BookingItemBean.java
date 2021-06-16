package com.NeverStarve.booking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BookingItems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingItemBean {

	Integer seqNo; //序號
	
	Integer bookingNo; //預約單號
	
	Date bookingDate; //預約日期
	
	Integer PeopleNum; //預約人數
	
	String Description; //店家預約資訊
}
