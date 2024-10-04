package com.example.myapp.controllers.admin;

import com.example.myapp.domain.entities.Order;
import com.example.myapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/admin")
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final OrderRepository orderRepository;

    @GetMapping("totalOrders")
    public long getTotalOrders() {
        return orderRepository.count();
    }

    @GetMapping("/totalPayingAmount")
    public BigDecimal getTotalPayingAmount() {
        List<Order> orders = orderRepository.findAll();
        BigDecimal totalPayingAmount = orders.stream()
                .map(Order::getPayingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPayingAmount;
    }
}
