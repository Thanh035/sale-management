package com.example.demo.domain.dto.admin.order;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.example.demo.domain.dto.admin.customer.CustomerDetailDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftOrderViewDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;
	private String lastModifiedBy;
	private Instant lastModifiedDate;

	private List<OrderDetailDTO> orderDetails;

	private CustomerDetailDTO customerDetail;

	private String note;

}
