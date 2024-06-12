package com.example.myapp.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.myapp.dto.admin.order.DraftOrderDTO;
import com.example.myapp.dto.admin.order.DraftOrderViewDTO;
import com.example.myapp.dto.admin.order.OrderDetailDTO;
import com.example.myapp.model.Order;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DraftOrderDTOMapper implements Function<Order, DraftOrderDTO> {

	private final OrderDetailDTOMapper orderDetailDTOMapper;

	@Override
	public DraftOrderDTO apply(Order t) {
		DraftOrderDTO dto = new DraftOrderDTO();
		BeanUtils.copyProperties(t, dto);
		return dto;
	}

	public DraftOrderViewDTO toDraftOrderViewDTO(Order t) {
		DraftOrderViewDTO dto = new DraftOrderViewDTO();
		BeanUtils.copyProperties(t, dto);
		if (t.getOrderDetails() != null) {
			List<OrderDetailDTO> orderDetails = new ArrayList<>();

			t.getOrderDetails().forEach(item -> {
				orderDetails.add(orderDetailDTOMapper.apply(item));
			});

			dto.setOrderDetails(orderDetails);
		}
		return dto;
	}

}
