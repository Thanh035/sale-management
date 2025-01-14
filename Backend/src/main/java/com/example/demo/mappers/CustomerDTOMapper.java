package com.example.demo.mappers;

import com.example.demo.domain.dto.admin.customer.CustomerDTO;
import com.example.demo.domain.dto.admin.customer.CustomerDetailDTO;
import com.example.demo.domain.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

    @Override
    public CustomerDTO apply(Customer t) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(t, dto);
        if (t.getCustomerAddresses().size() > 0) {
            dto.setAddress(t.getCustomerAddresses().get(0).getAddress());
        }
        if(t.getOrders().size() > 0) {
            dto.setOrderCount(t.getOrders().size());
            dto.setLastOrder(t.getOrders().get(0).getCode());
        }
        return dto;
    }

    public CustomerDetailDTO toCustomerDetail(Customer t) {
        CustomerDetailDTO dto = new CustomerDetailDTO();
        BeanUtils.copyProperties(t, dto);
        dto.setAddress(t.getCustomerAddresses().get(0).getAddress());
        dto.setPhoneNumber(t.getCustomerAddresses().get(0).getPhoneNumber());
        dto.setProvinceName(t.getCustomerAddresses().get(0).getProvince().getName());
        dto.setDistrictName(t.getCustomerAddresses().get(0).getDistrict().getName());
        dto.setWardName(t.getCustomerAddresses().get(0).getWard().getName());
        return dto;
    }

}
