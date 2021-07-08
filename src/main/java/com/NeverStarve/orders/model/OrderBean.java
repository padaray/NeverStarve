package com.NeverStarve.orders.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.StoreBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ORDERS")
//@Data
@Getter
@Setter
@ToString(exclude={"memberBean","OrderListBean","storeBean"})
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkOrderId; // 訂單ID
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_StoreBean_Id")
	private StoreBean storeBean; // 建立與店家的關聯
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_MemberBean_Id")
	private MemberBean memberBean; // 建立與會員的關聯
	
	String shipping_address; // 購買者的地址
	String order_note; // 訂單備註
	Double totalCost; // 整個訂單的總價
	LocalDateTime orderDate; // 購買日期
	Integer trading; // 交易成功的判斷

	@Transient
	String ItemName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderBean", fetch =FetchType.LAZY)
	private Set<OrderListBean> OrderListBean = new LinkedHashSet<>(); // 建立與會員的關聯

	// 訂單編號 日期 總價 訂單狀態 付款狀態

}
