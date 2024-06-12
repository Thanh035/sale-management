package com.example.myapp.mapper;

import com.example.myapp.dto.admin.customer.CustomerDTO;
import com.example.myapp.dto.admin.customer.CustomerDetailDTO;
import com.example.myapp.model.Customer;
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
        return dto;
    }

    public CustomerDetailDTO toCustomerDetail(Customer t) {
        CustomerDetailDTO dto = new CustomerDetailDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

}
