package com.NeverStarve.orders.model;

import java.time.LocalDate;
import java.util.Set;

import com.NeverStarve.member.model.MemberBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCar {
	
	Integer productID;       	//產品的ID
	Double  productOnePrice; 	//產品的單價
	Integer productQuantity;	//產品的數量
	Double  productOneTotal;    //產品的單價總額
	Double  productAllTotal;	//訂單的總金額

}
