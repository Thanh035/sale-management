package com.example.demo.domain.dto.admin.collection;

import java.io.Serializable;
import java.util.List;

import com.example.demo.domain.dto.admin.product.SalesProductDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String description;

	private List<SalesProductDTO> products;

}
