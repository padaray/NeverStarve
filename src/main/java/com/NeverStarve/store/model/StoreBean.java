package com.NeverStarve.store.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.LinkedHashSet;
import java.util.List;
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

import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.orders.model.OrderBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="STORE")
//@Data
@Getter
@Setter
@ToString(exclude={"menus","order"})
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class StoreBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	String storeCity;
	@Transient
	String storeTown;
	
	@NotBlank
	String storeAddress;
	@Pattern(regexp = "^[0-9]{7,12}$", message="只能填入數字")
	String storePhone;
	
	@JsonIgnore
	Blob coverImage;
	
	@Transient
	MultipartFile storeImage;
	
	String storeImageName;
	
	//0是停權 1是正常店家 2是付費廣告店家
	Integer storeLv;
	
	@Transient
	String base64;
	@Transient
	List<String> storeTypeList;
	
	String storeType;
	@NotNull
	Integer seatNumber;
	@Transient
	long totalcount;
	
	
	@JsonIgnoreProperties(value = "userInfo")//避免遞歸死循環
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.LAZY)
	private Set<MenuBean> menus =new LinkedHashSet<>();
	@JsonIgnore
	@OneToMany(mappedBy ="storeBean",fetch = FetchType.LAZY)
	private Set<OrderBean> order =new LinkedHashSet<>();
	
}
