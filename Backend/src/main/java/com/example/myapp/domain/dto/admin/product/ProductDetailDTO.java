package com.example.myapp.domain.dto.admin.product;

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
public class ProductDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String productName;
	private String productImageId;
	private String description;

	private String sku;
	private String barcode;
	private Integer quantity;

	private String categoryName;
	private String supplierName;

	private BigDecimal sellingPrice;
	private BigDecimal comparePrice;
	private BigDecimal capitalPrice;
	private Boolean allowOrders;

}
