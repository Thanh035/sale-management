package com.example.demo.domain.dto.admin.user;

import java.io.Serializable;

import javax.validation.constraints.*;

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
public class UserCreateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Họ tên không được để trống")
	@Size(max = 100,message = "Họ tên không được vượt quá 100 ký tự")
	private String fullname;

	@NotBlank(message = "Email không được bỏ trống")
	@Email(message = "Email không hợp lệ")
	private String email;

}
