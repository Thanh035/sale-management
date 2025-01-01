package com.example.demo.domain.dto.admin.role;

import java.io.Serializable;
import java.time.Instant;

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
public class RoleDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String code;
	private Instant createdDate;
	private String createdBy;

}
