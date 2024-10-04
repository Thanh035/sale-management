package com.example.myapp.repositories;

import com.example.myapp.domain.entities.Order;
import com.example.myapp.domain.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteByOrder(Order order);
}
