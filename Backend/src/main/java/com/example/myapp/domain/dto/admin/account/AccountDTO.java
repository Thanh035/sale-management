package com.example.myapp.domain.dto.admin.account;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.myapp.constants.Constants;

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
public class AccountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(max = 100)
	private String fullname;

	@Size(min = 9, max = 11)
	private String phoneNumber;
	
	private String profileImageId;

	private Set<String> roles;
}
