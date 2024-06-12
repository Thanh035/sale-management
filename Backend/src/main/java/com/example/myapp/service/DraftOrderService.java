package com.example.myapp.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.dto.admin.order.DraftOrderDTO;
import com.example.myapp.dto.admin.order.DraftOrderRequestDTO;
import com.example.myapp.dto.admin.order.DraftOrderViewDTO;
import com.example.myapp.dto.admin.order.OrderDetailDTO;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.mapper.DraftOrderDTOMapper;
import com.example.myapp.model.Order;
import com.example.myapp.model.OrderDetail;
import com.example.myapp.repository.OrderDetailRepository;
import com.example.myapp.repository.OrderRepository;
import com.example.myapp.repository.ProductRepository;
import com.example.myapp.util.CodeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DraftOrderService {

	private final OrderRepository orderRepository;

	private final DraftOrderDTOMapper draftOrderDTOMapper;

	private final ProductRepository productRepository;

	private final OrderDetailRepository orderDetailRepository;

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
		Optional<Order> orderOptional = orderRepository.findOneWithOrderDetailsById(id);
		if (orderOptional.isPresent()) {
			return orderOptional.map(draftOrderDTOMapper::toDraftOrderViewDTO).get();
		} else {
			throw new ResourceNotFoundException(String.format("Draft order with id [%s] not found", id));
		}
	}

}
