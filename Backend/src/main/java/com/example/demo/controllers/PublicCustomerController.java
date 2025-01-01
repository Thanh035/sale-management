package com.example.demo.controllers;

import com.example.demo.domain.dto.admin.customer.CustomerDTO;
import com.example.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class PublicCustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

}
