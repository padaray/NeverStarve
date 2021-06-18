package com.NeverStarve.orders.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCar implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Integer productID;       	//產品的ID
	Double  productOnePrice; 	//產品的單價
	Integer productQuantity;	//產品的數量
	Double  productOneTotal;    //產品的單價總額
	Double  productAllTotal;	//訂單的總金額

}
