package com.example.myapp.dto.admin.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private String code;

    private Instant createdDate;

    private String customerName;

    private String paymentType;

    private BigDecimal payingAmount;
}
