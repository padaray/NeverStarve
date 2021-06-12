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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Transient;

import com.NeverStarve.orders.model.OrderBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@NotBlank
	String storeName;
	@NotBlank
	String storeAccount;
	@Pattern(regexp = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z]).*$", message="至少8碼的英數字")
	String storePassword;
	@Transient
	String storeCheckPassword;
	@NotBlank
	String storeAddress;
	@Pattern(regexp = "^[0-9]{7,10}*$", message="只能填入數字")
	String storePhone;
	
	@JsonIgnore
	Blob storeImage;
	@NotBlank
	String storeType;
	@Pattern(regexp = "^[0-9]*$", message="只能填入數字")
	Integer seatNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.LAZY)
	private Set<MenuBean> menus =new LinkedHashSet<>();
	
	

	
}
