package com.example.myapp.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.constant.RolesConstants;
import com.example.myapp.dto.admin.order.OrderDTO;
import com.example.myapp.dto.admin.order.OrderViewDTO;
import com.example.myapp.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1.0/admin/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@Value("${application.name}")
	private String applicationName;

	@GetMapping
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF
			+ "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public List<OrderDTO> getAllOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
			@RequestParam(name = "filter", required = false) String filter) {
		log.debug("REST request to get all Orders for an admin");

		Page<OrderDTO> page = orderService.getAllOrders(filter, pageable);
		return page.getContent();
	}

	@GetMapping("{orderId}")
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF
			+ "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public OrderViewDTO getOrder(@PathVariable Long orderId) {
		log.debug("REST request to get order : {}", orderId);
		return orderService.getOrderWithOrderDetailsById(orderId);
	}

}
