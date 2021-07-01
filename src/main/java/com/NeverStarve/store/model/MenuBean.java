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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Entity
@Table(name ="MENU")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkDishId;
	@NotBlank
	String dishName;
//	@Pattern(regexp = "", message="只能填入數字")
	@Min(0)
	Double dishPrice;
	@NotBlank
	String dishIntroduction;
	@JsonIgnore
	Blob dishPicture;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;

	@Transient
	Integer quantity;
	
	
	
}
