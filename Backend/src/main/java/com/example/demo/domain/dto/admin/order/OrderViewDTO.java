package com.example.demo.domain.dto.admin.order;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderViewDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;
	private String paymentType;
	private Instant lastModifiedDate;

	private String lastModifiedBy;

	private List<OrderDetailDTO> orderDetails;

	private String note;
	
	private String paymentMethod;

}
