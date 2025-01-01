package com.example.demo.domain.dto.admin.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idProduct;

    private String productName;
    private Integer numberItems;

    private BigDecimal sellingPrice;

    private BigDecimal unitPrice;

}
