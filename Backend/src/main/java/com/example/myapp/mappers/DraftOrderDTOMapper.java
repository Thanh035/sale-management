package com.example.myapp.mappers;

import com.example.myapp.domain.dto.admin.order.DraftOrderDTO;
import com.example.myapp.domain.dto.admin.order.DraftOrderViewDTO;
import com.example.myapp.domain.dto.admin.order.OrderDetailDTO;
import com.example.myapp.domain.entities.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class DraftOrderDTOMapper implements Function<Order, DraftOrderDTO> {

    private final OrderDetailDTOMapper orderDetailDTOMapper;

    private final CustomerDTOMapper customerDTOMapper;

    @Override
    public DraftOrderDTO apply(Order t) {
        DraftOrderDTO dto = new DraftOrderDTO();
        BeanUtils.copyProperties(t, dto);
        if (t.getCustomer() != null) {
            dto.setCustomerName(t.getCustomer().getFullName());
        }
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
        if (t.getCustomer() != null) {
            dto.setCustomerDetail(customerDTOMapper.toCustomerDetail(t.getCustomer()));
        }
        return dto;
    }

}
