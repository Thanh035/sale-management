package com.example.myapp.domain.dto.admin.order;

import com.example.myapp.domain.dto.admin.customer.CustomerDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<OrderDetailDTO> items;

    private String note;

    private CustomerDetailDTO customerDetail;

    private String paymentMethod;

    private String paymentStatus;



}
