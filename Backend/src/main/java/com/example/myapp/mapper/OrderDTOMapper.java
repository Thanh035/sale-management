package com.example.myapp.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.myapp.dto.admin.order.OrderDTO;
import com.example.myapp.dto.admin.order.OrderDetailDTO;
import com.example.myapp.dto.admin.order.OrderViewDTO;
import com.example.myapp.model.Order;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderDTOMapper implements Function<Order, OrderDTO> {

	private final OrderDetailDTOMapper orderDetailDTOMapper;

	@Override
	public OrderDTO apply(Order t) {
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(t, dto);
		return dto;
	}

	public OrderViewDTO toOrderViewDTO(Order t) {
		OrderViewDTO dto = new OrderViewDTO();
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
