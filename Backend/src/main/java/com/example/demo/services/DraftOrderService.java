package com.example.demo.services;

import com.example.demo.domain.dto.admin.order.DraftOrderDTO;
import com.example.demo.domain.dto.admin.order.DraftOrderRequestDTO;
import com.example.demo.domain.dto.admin.order.DraftOrderViewDTO;
import com.example.demo.domain.dto.admin.order.OrderDetailDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mappers.DraftOrderDTOMapper;
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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DraftOrderService {

    private final OrderRepository orderRepository;

    private final DraftOrderDTOMapper draftOrderDTOMapper;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Page<DraftOrderDTO> getAllDraftOrders(String filter, Pageable pageable) {
//	        if (filter != null && !filter.isEmpty()) {
//	            return orderRepository.findAllWithFilterByStatusFalse(filter, pageable).map(draftOrderDTOMapper);
//	        } else {
        return orderRepository.findAllByStatusFalse(pageable).map(draftOrderDTOMapper);
//	        }
    }

    public DraftOrderDTO createDraftOrder(DraftOrderRequestDTO request) {
        Order order = new Order();
        order.setNote(request.getNote());
        order.setStatus(false);
        order.setCode("#D" + CodeUtil.generateCodeByTime());
        BigDecimal payingAmount = BigDecimal.ZERO;
        for (OrderDetailDTO item : request.getItems()) {
            payingAmount = payingAmount.add(item.getUnitPrice());
        }
        order.setPayingAmount(payingAmount);

        var customer = customerRepository.findById(request.getCustomerDetail().getId());
        order.setCustomer(customer.get());

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
            });
        });


        orderRepository.save(newOrder);

        log.debug("Created Information For Order: {}", request);
        return draftOrderDTOMapper.apply(newOrder);
    }

    public void deleteDraftOrderById(Long id) {
        checkIfDraftOrderExistsOrThrow(id);

        orderRepository.findById(id).ifPresent(draftOrder -> {
            orderDetailRepository.deleteByOrder(draftOrder);
            orderRepository.delete(draftOrder);
            log.debug("Deleted Draft order: {}", draftOrder);
        });

    }

    public void deleteDraftOrders(Long[] ids) {
        for (Long id : ids) {
            deleteDraftOrderById(id);
        }
    }

    private void checkIfDraftOrderExistsOrThrow(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("draft order with id [%s] not found", id));
        }
    }

    public Optional<DraftOrderDTO> updateDraftOrder(DraftOrderRequestDTO updateRequest, Long draftOrderId) {
        return Optional.of(orderRepository.findById(draftOrderId))
                .filter(Optional::isPresent)
                .map(Optional::get).map(draftOrder -> {
                    draftOrder.setNote(updateRequest.getNote());
//			           updateRequest.getItems().forEach(item -> {
//			        	   
//			           });

                    log.debug("Changed Information for Draft Order: {}", draftOrder);
                    return draftOrder;
                }).map(draftOrderDTOMapper);
    }

    @Transactional(readOnly = true)
    public DraftOrderViewDTO getDraftOrderWithOrderDetailsById(Long id) {
        Optional<Order> orderOptional = orderRepository.findOneWithOrderDetailsAndCustomerById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.map(draftOrderDTOMapper::toDraftOrderViewDTO).get();
        } else {
            throw new ResourceNotFoundException(String.format("Draft order with id [%s] not found", id));
        }
    }

}
