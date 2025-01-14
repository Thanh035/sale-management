package com.example.demo.services;

import com.example.demo.domain.dto.admin.order.OrderDTO;
import com.example.demo.domain.dto.admin.order.OrderDetailDTO;
import com.example.demo.domain.dto.admin.order.OrderRequestDTO;
import com.example.demo.domain.dto.admin.order.OrderViewDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mappers.OrderDTOMapper;
import com.example.demo.domain.entities.Order;
import com.example.demo.domain.entities.OrderDetail;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderDetailRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.utils.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderDTOMapper orderDTOMapper;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

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

    public OrderDTO createOrder(OrderRequestDTO request) {
        Order order = new Order();
        order.setNote(request.getNote());
        order.setStatus(false);
        order.setCode("#D" + CodeUtil.generateCodeByTime());
        BigDecimal payingAmount = BigDecimal.ZERO;
        for (OrderDetailDTO item : request.getItems()) {
            payingAmount = payingAmount.add(item.getUnitPrice());
        }
        order.setPayingAmount(payingAmount);
        order.setPaymentType(request.getPaymentStatus());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(true);

        if (request.getCustomerDetail().getId() != null) {
            var customer = customerRepository.findById(request.getCustomerDetail().getId());
            order.setCustomer(customer.get());
        }

        // Create order to csdl
        var newOrder = orderRepository.saveAndFlush(order);

        // Create order detail
        request.getItems().stream().forEach(item -> {
            productRepository.findById(item.getIdProduct()).ifPresent(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setProductName(item.getProductName());
                orderDetail.setProductPrice(item.getSellingPrice());
                if (product.getProductImageId() != null) {
                    orderDetail.setProductImageId(product.getProductImageId());
                }
                orderDetail.setQuantity(item.getNumberItems());
                orderDetail.setUnitPrice(item.getUnitPrice());
                orderDetail.setOrder(newOrder);
                orderDetailRepository.save(orderDetail);
                product.setQuantity(product.getQuantity() - item.getNumberItems());
            });
        });
        orderRepository.save(newOrder);

        log.debug("Created Information For Order: {}", request);
        return orderDTOMapper.apply(newOrder);
    }

    public void paymentOrder(Long orderId, String paymentMethod) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(true);
            order.setPaidDate(Instant.now());
            order.setPaymentMethod(paymentMethod);
            log.debug("Changed payment for Order: {}", paymentMethod);
        });
    }
}
