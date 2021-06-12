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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MEMBER")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

@JsonIgnore
@OneToMany(mappedBy ="memberBean")
private Set<OrderBean> orders =new LinkedHashSet<>();



}