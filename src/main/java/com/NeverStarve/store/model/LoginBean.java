package com.NeverStarve.store.model;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBean {
	
	@NotBlank(message = "請輸入帳號")
	String storeAccount;
	@NotBlank(message = "請輸入密碼")
	String storePassword;
	@Transient
	String accountOrPasswordError;
}
