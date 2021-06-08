package com.NeverStarve.orders.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDERLIST")
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

	public OrderListBean() {
		super();
	}

	public OrderListBean(Integer pkseqno, String product_name, Integer quantity, String description, Integer unitprice,
			OrderBean orderBean, Integer trading) {
		super();
		this.pkseqno = pkseqno;
		this.product_name = product_name;
		this.quantity = quantity;
		this.description = description;
		this.unitprice = unitprice;
		this.orderBean = orderBean;
		this.trading = trading;
	}

	public Integer getPkseqno() {
		return pkseqno;
	}

	public void setPkseqno(Integer pkseqno) {
		this.pkseqno = pkseqno;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Integer unitprice) {
		this.unitprice = unitprice;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public Integer getTrading() {
		return trading;
	}

	public void setTrading(Integer trading) {
		this.trading = trading;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderListBean [pkseqno=");
		builder.append(pkseqno);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", description=");
		builder.append(description);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", orderBean=");
		builder.append(orderBean);
		builder.append(", trading=");
		builder.append(trading);
		builder.append("]");
		return builder.toString();
	}


}
