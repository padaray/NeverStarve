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
	
	Integer StoreID;			//找店家的ID
	Integer productID;       	//產品的ID
	Integer productQuantity;	//產品的數量

}
