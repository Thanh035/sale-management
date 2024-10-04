package com.example.myapp.domain.dto.admin.order;

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

    private String paymentStatus;

    private BigDecimal payingAmount;
}
