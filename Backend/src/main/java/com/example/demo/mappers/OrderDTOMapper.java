package com.example.demo.mappers;

import com.example.demo.domain.dto.admin.order.OrderDTO;
import com.example.demo.domain.dto.admin.order.OrderDetailDTO;
import com.example.demo.domain.dto.admin.order.OrderViewDTO;
import com.example.demo.domain.entities.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class OrderDTOMapper implements Function<Order, OrderDTO> {

    private final OrderDetailDTOMapper orderDetailDTOMapper;

    @Override
    public OrderDTO apply(Order t) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(t, dto);
        if (t.getCustomer() != null) {
            dto.setCustomerName(t.getCustomer().getFullName());
        }
        dto.setPaymentStatus(t.getPaymentType());
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
