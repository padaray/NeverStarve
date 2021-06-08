package com.NeverStarve.orders.model;

import java.io.Serializable;
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

@Entity
@Table(name ="ORDERS")
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
	
	public OrderBean() {
		super();
	}
	
	

	public OrderBean(Integer pkOrderId, StoreBean storeBean, MemberBean memberBean, Set<OrderListBean> items,
			String shipping_address, String order_note, String totalCost) {
		super();
		this.pkOrderId = pkOrderId;
		this.storeBean = storeBean;
		this.memberBean = memberBean;
		this.items = items;
		this.shipping_address = shipping_address;
		this.order_note = order_note;
		this.totalCost = totalCost;
	}



	public Integer getPkOrderId() {
		return pkOrderId;
	}

	public void setPkOrderId(Integer pkOrderId) {
		this.pkOrderId = pkOrderId;
	}

	public StoreBean getStoreBean() {
		return storeBean;
	}

	public void setStoreBean(StoreBean storeBean) {
		this.storeBean = storeBean;
	}

	public MemberBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}

	public Set<OrderListBean> getItems() {
		return items;
	}

	public void setItems(Set<OrderListBean> items) {
		this.items = items;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getOrder_note() {
		return order_note;
	}

	public void setOrder_note(String order_note) {
		this.order_note = order_note;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderBean [pkOrderId=");
		builder.append(pkOrderId);
		builder.append(", storeBean=");
		builder.append(storeBean);
		builder.append(", memberBean=");
		builder.append(memberBean);
		builder.append(", items=");
		builder.append(items);
		builder.append(", shipping_address=");
		builder.append(shipping_address);
		builder.append(", order_note=");
		builder.append(order_note);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append("]");
		return builder.toString();
	}
	
	

	

}
