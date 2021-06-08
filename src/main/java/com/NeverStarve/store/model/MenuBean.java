package com.NeverStarve.store.model;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="MENU")
public class MenuBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkDishId;
	String dishName;
	Double dishPrice;
	String dishIntroduction;
	Blob dishPicture;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;

	public MenuBean() {
	
	}

	public MenuBean(Integer pkDishId, String dishName, Double dishPrice, String dishIntroduction, Blob dishPicture,
			StoreBean storeBean) {
		super();
		this.pkDishId = pkDishId;
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.dishIntroduction = dishIntroduction;
		this.dishPicture = dishPicture;
		this.storeBean = storeBean;
	}

	public Integer getPkDishId() {
		return pkDishId;
	}

	public void setPkDishId(Integer pkDishId) {
		this.pkDishId = pkDishId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public Double getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(Double dishPrice) {
		this.dishPrice = dishPrice;
	}

	public String getDishIntroduction() {
		return dishIntroduction;
	}

	public void setDishIntroduction(String dishIntroduction) {
		this.dishIntroduction = dishIntroduction;
	}

	public Blob getDishPicture() {
		return dishPicture;
	}

	public void setDishPicture(Blob dishPicture) {
		this.dishPicture = dishPicture;
	}

	public StoreBean getStoreBean() {
		return storeBean;
	}

	public void setStoreBean(StoreBean storeBean) {
		this.storeBean = storeBean;
	}
	
	
	
	
	
	
	
	
}
