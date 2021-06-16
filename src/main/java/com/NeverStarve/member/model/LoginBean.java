package com.NeverStarve.member.model;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBean {
	
	@NotBlank(message = "帳號不得為空")
	String email;
	@NotBlank(message = "密碼不得為空")
	String password;
	@Transient
	String emailOrPasswordError;
}
