package com.example.myapp.domain.dto.admin.customer;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String fullName;
	private String address;

	private String email;

	private Integer orderCount;

	private String lastOrder;

	private String debt;

	private String totalSpent;

}
