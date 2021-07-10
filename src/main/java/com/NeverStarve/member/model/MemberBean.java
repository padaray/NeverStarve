package com.NeverStarve.member.model;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="MEMBER")
//@Data
@Getter
@Setter
@ToString(exclude={"orders"})
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
@NotBlank(message = "地址不得空白")
String address;
@Transient
//@NotBlank(message = "請選擇縣市地區")
String memberCity;
@Transient
//@NotBlank
String memberTown;
@NotBlank
@Email(message = "必須是形式完整的電子郵件")
String email;
@Pattern(regexp= PHONE_REG , message="請輸入正確手機號碼")
String mobilePhone;

String userType;

LocalDate registerTime;
//Timestamp registerTime;
Date longTime;//登入時間
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
@OneToMany(mappedBy ="memberBean",fetch = FetchType.LAZY)
private Set<OrderBean> orders =new LinkedHashSet<>();



}