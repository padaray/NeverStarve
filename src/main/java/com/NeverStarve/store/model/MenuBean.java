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

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Entity
@Table(name ="MENU")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer pkDishId;
	@NotBlank
	String dishName;
	@Min(0)
	Double dishPrice;
	@NotBlank
	String dishIntroduction;
	
	@JsonIgnore
	Blob coverImage;
	
	@Transient
	MultipartFile dishPicture;
	
	String dishImageName;
	
	@Transient
	String base64;
	
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, 
            optional = false,fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_Store_Id")
	private StoreBean storeBean;

	@Transient
	Integer quantity;
	
	
	
}
