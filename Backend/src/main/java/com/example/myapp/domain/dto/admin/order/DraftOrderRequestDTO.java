package com.example.myapp.domain.dto.admin.order;

import com.example.myapp.domain.dto.admin.customer.CustomerDetailDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftOrderRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<OrderDetailDTO> items;

    private String note;

    private CustomerDetailDTO customerDetail;

}
