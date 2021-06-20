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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



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
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message="請輸入至少8個字包含一個英文及數字")
	String storePassword;
	@Transient
	String storeCheckPassword;
	@Transient
	@NotBlank
	String storeCity;
	@Transient
	@NotBlank
	String storeTown;
	@NotBlank
	String storeAddress;
	@Pattern(regexp = "^[0-9]{7,12}$", message="只能填入數字")
	String storePhone;
	
	@JsonIgnore
	Blob storeImage;
	@NotBlank
	String storeType;
	@NotNull
	Integer seatNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.LAZY)
	private Set<MenuBean> menus =new LinkedHashSet<>();
	
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.LAZY)
	private Set<OrderBean> order =new LinkedHashSet<>();
	
}
