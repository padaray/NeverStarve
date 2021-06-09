package com.NeverStarve.store.model;

import java.sql.Blob;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.NeverStarve.orders.model.OrderBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="STORE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkStoreId;
	String storeName;
	String storeAccount;
	String storePassword;
	String storeAddress;
	String storePhone;
	Blob storeImage;
	String storeType;
	Integer seatNumber;
	
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.EAGER)
	private Set<OrderBean> orders =new LinkedHashSet<>();
	
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.EAGER)
	private Set<MenuBean> menus =new LinkedHashSet<>();
	
	

	
}
