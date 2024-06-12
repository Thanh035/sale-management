package com.example.myapp.repository;

import com.example.myapp.model.Order;
import com.example.myapp.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteByOrder(Order order);
}
