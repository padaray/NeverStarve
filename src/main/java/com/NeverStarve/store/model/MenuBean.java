package com.NeverStarve.store.model;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="MENU")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkDishId;
	String dishName;
	Double dishPrice;
	String dishIntroduction;
	@JsonIgnore
	Blob dishPicture;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;

	
	
	
	
	
}
