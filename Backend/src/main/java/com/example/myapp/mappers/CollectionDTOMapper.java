package com.example.myapp.mappers;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.dto.admin.collection.CollectionDTO;
import com.example.myapp.domain.dto.admin.collection.CollectionDetailDTO;
import com.example.myapp.domain.entities.Collection;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CollectionDTOMapper implements Function<Collection, CollectionDTO> {

	private final ProductDTOMapper productDTOMapper;

	@Override
	public CollectionDTO apply(Collection t) {
		CollectionDTO dto = new CollectionDTO();
		BeanUtils.copyProperties(t, dto);
		if(t.getProducts() != null) {
			dto.setProductCount(t.getProducts().size());
		}
		return dto;
	}

	public CollectionDetailDTO toCollectionDetailDTO(Collection t) {
		CollectionDetailDTO dto = new CollectionDetailDTO();
		BeanUtils.copyProperties(t, dto);
		if (t.getProducts() != null && !t.getProducts().isEmpty()) {
			dto.setProducts(
					t.getProducts().stream().map(productDTOMapper::toSalesProductDTO).collect(Collectors.toList()));
		}
		return dto;
	}

}
