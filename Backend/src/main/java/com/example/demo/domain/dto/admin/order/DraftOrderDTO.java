package com.example.demo.domain.dto.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;

	private Instant createdDate;
	private Instant lastModifiedDate;	

	private String customerName;

	private Boolean status;

	private BigDecimal payingAmount;
}
