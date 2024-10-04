package com.example.myapp.domain.dto.admin.account;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
}
