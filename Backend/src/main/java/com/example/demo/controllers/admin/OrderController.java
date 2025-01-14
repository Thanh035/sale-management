package com.example.demo.controllers.admin;

import com.example.demo.constants.RolesConstants;
import com.example.demo.domain.dto.admin.order.OrderDTO;
import com.example.demo.domain.dto.admin.order.OrderRequestDTO;
import com.example.demo.domain.dto.admin.order.OrderViewDTO;
import com.example.demo.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

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

    @PostMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public OrderDTO createOrder(@Valid @RequestBody OrderRequestDTO request) throws URISyntaxException {
        log.debug("REST request to save Order : {}", request);
        return orderService.createOrder(request);
    }

//    @PatchMapping(value = "/{orderId}")
//    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')"
//            public void paymentOrder(@Valid @RequestBody String paymentMethod, @PathVariable("orderId") Long orderId) {
//        orderService.paymentOrder(orderId, paymentMethod);
//    }

}
