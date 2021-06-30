package com.NeverStarve.orders.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.NeverStarve.store.model.MenuBean;
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
	Integer pkOrderListId; 		//訂單詳細資訊
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_MenuBean_Id")
	private MenuBean menuBean; 	//取得菜單的Bean
	Integer quantity ;			//商品數量
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_OrderBean_Id")
	private OrderBean orderBean; //取得訂單的Bean
	
	@Transient
	Integer menuID;
	
	//商品名稱 商品ID 購買數量 單價 單項總價 購買日期

}
