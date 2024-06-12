package com.example.myapp.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.dto.admin.order.DraftOrderRequestDTO;
import com.example.myapp.dto.admin.order.OrderDTO;
import com.example.myapp.dto.admin.order.OrderViewDTO;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.mapper.OrderDTOMapper;
import com.example.myapp.model.Order;
import com.example.myapp.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	private final OrderDTOMapper orderDTOMapper;

	@Transactional(readOnly = true)
	public Page<OrderDTO> getAllOrders(String filter, Pageable pageable) {
		return orderRepository.findAllByStatusTrue(pageable).map(orderDTOMapper);
	}

	@Transactional(readOnly = true)
	public OrderViewDTO getOrderWithOrderDetailsById(Long id) {
		Optional<Order> orderOptional = orderRepository.findOneWithOrderDetailsById(id);
		if (orderOptional.isPresent()) {
			return orderOptional.map(orderDTOMapper::toOrderViewDTO).get();
		} else {
			throw new ResourceNotFoundException(String.format("Draft order with id [%s] not found", id));
		}
	}

	public OrderDTO createOrder(DraftOrderRequestDTO request) {
		Order order = new Order();
		BeanUtils.copyProperties(request, order);
		var newOrder = orderRepository.save(order);
		log.debug("Created Information For Order: {}", request);
		return orderDTOMapper.apply(newOrder);
	}

}
