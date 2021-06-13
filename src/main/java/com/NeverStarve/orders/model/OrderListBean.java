package com.NeverStarve.orders.model;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name ="ORDERList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkOrderListId; 		//商品ID
	String product_name; 		//商品名稱
	String quantity ;			//商品數量
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_OrderBean_Id")
	private OrderBean orderBean; //取得訂單的Bean
	Double productPrice; 		//單價
	Double onetotalCost; 		//單項總價
	LocalDate  buyDate;			//購買日期
	
	
	
	//商品名稱 商品ID 購買數量 單價 單項總價 購買日期

}
