package com.example.myapp.controllers;

import com.example.myapp.domain.dto.admin.customer.CustomerDTO;
import com.example.myapp.services.CustomerService;
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
