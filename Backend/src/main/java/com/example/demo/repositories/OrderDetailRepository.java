package com.example.demo.repositories;

import com.example.demo.domain.entities.Order;
import com.example.demo.domain.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteByOrder(Order order);
}
