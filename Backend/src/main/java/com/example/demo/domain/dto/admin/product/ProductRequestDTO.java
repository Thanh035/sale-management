package com.example.demo.domain.dto.admin.product;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class ProductRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productName;

	private Integer categoryId;
	private String categoryName = "Khác";
	private String supplierName = "Khác";
	private String description;
	private BigDecimal sellingPrice = BigDecimal.ZERO;
	private BigDecimal comparePrice = BigDecimal.ZERO;
	private BigDecimal capitalPrice = BigDecimal.ZERO;

	private String sku;
	private String barcode;
	private Boolean allowOrders = false;
	private Integer quantity = 0;

}
