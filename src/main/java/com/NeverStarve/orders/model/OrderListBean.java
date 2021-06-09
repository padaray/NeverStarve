package com.NeverStarve.orders.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ORDERLIST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkseqno;
	
	String product_name;
	
	Integer quantity ; 
	
	String description; 
	
	Integer unitprice ;
	
	@ManyToOne
	@JoinColumn(name = "pkOrderId")
	OrderBean orderBean;
	
	Integer trading ;

	

}
