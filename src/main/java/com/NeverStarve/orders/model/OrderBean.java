package com.NeverStarve.orders.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.NeverStarve.member.model.MemberBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="ORDERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkOrderId;
	String  storeId;
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_MemberBean_Id")
	private MemberBean memberBean;
	String shipping_address;
	String order_note;
	Double totalCost;
	Date  orderDate;
	String product_name;
	String quantity ; 
	


}
