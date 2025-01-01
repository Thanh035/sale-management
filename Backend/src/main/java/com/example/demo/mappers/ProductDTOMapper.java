package com.example.demo.mappers;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.admin.product.ProductDTO;
import com.example.demo.domain.dto.admin.product.ProductDetailDTO;
import com.example.demo.domain.dto.admin.product.ProductViewDTO;
import com.example.demo.domain.dto.admin.product.SalesProductDTO;
import com.example.demo.domain.entities.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductDTOMapper implements Function<Product, ProductDTO> {

	@Override
	public ProductDTO apply(Product product) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		if (product.getSupplier() != null) {
			dto.setSupplierName(product.getSupplier().getName());
		}
		if (product.getCategory() != null) {
			dto.setCategoryName(product.getCategory().getName());
		}
		return dto;
	}

	public ProductDetailDTO toProductDetailDTO(Product product) {
		ProductDetailDTO dto = new ProductDetailDTO();
		BeanUtils.copyProperties(product, dto);
		if (product.getSupplier() != null) {
			dto.setSupplierName(product.getSupplier().getName());
		}
		if (product.getCategory() != null) {
			dto.setCategoryName(product.getCategory().getName());
		}
		return dto;
	}

	public SalesProductDTO toSalesProductDTO(Product product) {
		SalesProductDTO dto = new SalesProductDTO();
		BeanUtils.copyProperties(product, dto);
		dto.setIdProduct(product.getId());
		return dto;
	}

	public ProductViewDTO toProductViewDTO(Product product) {
		ProductViewDTO dto = new ProductViewDTO();
		BeanUtils.copyProperties(product, dto);
		return dto;
	}

}
