package com.example.myapp.domain.dto.admin.account;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Size(max = 100)
	private String fullname;

	@Size(min = 9, max = 11)
	private String phoneNumber;

}
