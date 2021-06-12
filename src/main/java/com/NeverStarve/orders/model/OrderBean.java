package com.NeverStarve.orders.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;

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
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_MemberBean_Id")
	private MemberBean memberBean;
	
	@OneToMany(mappedBy="orderBean", cascade=CascadeType.ALL)
	private Set<OrderListBean> items = new LinkedHashSet<>();
	
	String shipping_address;
	
	String order_note;
	
	String totalCost;
  
    Date  orderDate;

}
