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

@Entity
@Table(name ="STORE")
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
	
	
	public StoreBean() {
		
		}


	public StoreBean(Integer pkStoreId, String storeName, String storeAccount, String storePassword,
			String storeAddress, String storePhone, Blob storeImage, String storeType, Integer seatNumber,
			Set<OrderBean> orders, Set<MenuBean> menus) {
		super();
		this.pkStoreId = pkStoreId;
		this.storeName = storeName;
		this.storeAccount = storeAccount;
		this.storePassword = storePassword;
		this.storeAddress = storeAddress;
		this.storePhone = storePhone;
		this.storeImage = storeImage;
		this.storeType = storeType;
		this.seatNumber = seatNumber;
		this.orders = orders;
		this.menus = menus;
	}




	public Integer getPkStoreId() {
		return pkStoreId;
	}


	public void setPkStoreId(Integer pkStoreId) {
		this.pkStoreId = pkStoreId;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public String getStoreAccount() {
		return storeAccount;
	}


	public void setStoreAccount(String storeAccount) {
		this.storeAccount = storeAccount;
	}


	public String getStorePassword() {
		return storePassword;
	}


	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}


	public String getStoreAddress() {
		return storeAddress;
	}


	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}


	public String getStorePhone() {
		return storePhone;
	}


	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}


	public Blob getStoreImage() {
		return storeImage;
	}


	public void setStoreImage(Blob storeImage) {
		this.storeImage = storeImage;
	}


	public Set<OrderBean> getOrders() {
		return orders;
	}


	public void setOrders(Set<OrderBean> orders) {
		this.orders = orders;
	}


	public Set<MenuBean> getMenus() {
		return menus;
	}


	public void setMenus(Set<MenuBean> menus) {
		this.menus = menus;
	}


	public String getStoreType() {
		return storeType;
	}


	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}


	public Integer getSeatNumber() {
		return seatNumber;
	}


	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	
	
}
