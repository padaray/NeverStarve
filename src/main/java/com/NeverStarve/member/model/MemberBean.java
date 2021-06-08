package com.NeverStarve.member.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.orders.model.OrderBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="MEMBER")
public class MemberBean implements Serializable{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "pkMemberId")
Integer pkMemberId;
String personId;
String name;
String password;
String address;
String email;
String mobilePhone;
String userType;
Timestamp registerTime;
Date longTime;
@JsonIgnore
Blob coverImage;
String fileName;
Double unpaid_amount;

@Transient
String bas64;

@Transient
private MultipartFile memberImage;

@OneToMany(mappedBy ="memberBean",fetch = FetchType.EAGER)
private Set<OrderBean> orders =new LinkedHashSet<>();

public MemberBean() {
	// TODO Auto-generated constructor stub
}



public MemberBean(Integer pkMemberId, String personId, String name, String password, String address, String email,
		String mobilePhone, String userType, Timestamp registerTime, Date longTime, Blob coverImage,
		String fileName, Double unpaid_amount, String bas64, MultipartFile memberImage, Set<OrderBean> orders) {
	super();
	this.pkMemberId = pkMemberId;
	this.personId = personId;
	this.name = name;
	this.password = password;
	this.address = address;
	this.email = email;
	this.mobilePhone = mobilePhone;
	this.userType = userType;
	this.registerTime = registerTime;
	this.longTime = longTime;
	this.coverImage = coverImage;
	this.fileName = fileName;
	this.unpaid_amount = unpaid_amount;
	this.bas64 = bas64;
	this.memberImage = memberImage;
	this.orders = orders;
}



public Integer getPkMemberId() {
	return pkMemberId;
}

public void setPkMemberId(Integer pkMemberId) {
	this.pkMemberId = pkMemberId;
}

public String getPersonId() {
	return personId;
}

public void setPersonId(String personId) {
	this.personId = personId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getMobilePhone() {
	return mobilePhone;
}

public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
}

public String getUserType() {
	return userType;
}

public void setUserType(String userType) {
	this.userType = userType;
}

public Timestamp getRegisterTime() {
	return registerTime;
}

public void setRegisterTime(Timestamp registerTime) {
	this.registerTime = registerTime;
}

public Date getLongTime() {
	return longTime;
}

public void setLongTime(Date longTime) {
	this.longTime = longTime;
}

public Blob getCoverImage() {
	return coverImage;
}

public void setCoverImage(Blob coverImage) {
	this.coverImage = coverImage;
}

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
}

public Double getUnpaid_amount() {
	return unpaid_amount;
}

public void setUnpaid_amount(Double unpaid_amount) {
	this.unpaid_amount = unpaid_amount;
}

public MultipartFile getMemberImage() {
	return memberImage;
}

public void setMemberImage(MultipartFile memberImage) {
	this.memberImage = memberImage;
}

public Set<OrderBean> getOrders() {
	return orders;
}

public void setOrders(Set<OrderBean> orders) {
	this.orders = orders;
}



public String getBas64() {
	return bas64;
}



public void setBas64(String bas64) {
	this.bas64 = bas64;
}




}