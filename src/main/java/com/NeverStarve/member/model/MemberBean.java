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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

	public static final String PHONE_REG = "09[0-9]{8}$";
	public static final String PERSONID_REG = "^[A-Z]{1}[1-2]{1}[0-9]{8}$";
	public static final String Password_REG = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	public static final String NAME_REG =	"^[\u4E00-\u9FA5]{2,}$";

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "pkMemberId")
Integer pkMemberId;

@Pattern(regexp= PERSONID_REG , message="請輸入正確身分證字號")
String personId;
@Pattern(regexp= NAME_REG , message="請輸入2個字以上繁體中文")
String name;
@Pattern(regexp= Password_REG , message="請輸入至少8個字包含一個英文及數字")
String password;
@Transient
String checkPassword;
@NotBlank
String address;
@NotBlank
@Email
String email;
@Pattern(regexp= PHONE_REG , message="請輸入正確手機號碼")
String mobilePhone;
String userType;
Timestamp registerTime;
Date longTime;
@JsonIgnore
Blob coverImage;

String fileName;

Double unpaid_amount;
@Transient
long totalcount;
@Transient
String bas64;
@Transient
private MultipartFile memberImage;

@JsonIgnore
@OneToMany(mappedBy ="memberBean")
private Set<OrderBean> orders =new LinkedHashSet<>();



}