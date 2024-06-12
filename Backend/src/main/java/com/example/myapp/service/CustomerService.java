package com.example.myapp.service;

import com.example.myapp.dto.admin.customer.CustomerDTO;
import com.example.myapp.dto.admin.customer.CustomerDetailDTO;
import com.example.myapp.dto.admin.customer.CustomerRequestDTO;
import com.example.myapp.dto.admin.customer.CustomerUpdateDTO;
import com.example.myapp.exception.DuplicateResourceException;
import com.example.myapp.mapper.CustomerDTOMapper;
import com.example.myapp.model.Customer;
import com.example.myapp.model.CustomerAddress;
import com.example.myapp.repository.CustomerAddressRepository;
import com.example.myapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerDTOMapper customerDTOMapper;

    private final CustomerAddressRepository customerAddressRepository;

    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAllCustomers(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
//			return customerRepository.findAllWithFilter(filter, pageable).map(customerDTOMapper);
            return customerRepository.findAll(pageable).map(customerDTOMapper);
        } else {
            return customerRepository.findAll(pageable).map(customerDTOMapper);
        }
    }

    @Transactional(readOnly = true)
    public Optional<CustomerDetailDTO> getCustomer(Long id) {
        return customerRepository.findById(id).map(customerDTOMapper::toCustomerDetail);
    }

    public CustomerDTO createCustomer(CustomerRequestDTO request) {
        Customer customer = new Customer();
        if (request.getEmail() != null) {
            checkIfEmailExistsOrThrow(request.getEmail());
        }
        if (request.getMobile() != null) {
            checkIfMobileAlreadyOrThrow(request.getMobile());
        }

        BeanUtils.copyProperties(request, customer);
        var newCustomer = customerRepository.save(customer);

        if (request.getAddress() != null || request.getPhoneNumber() != null) {
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setPhoneNumber(request.getPhoneNumber());
            customerAddress.setAddress(request.getAddress());
            customerAddress.setCustomer(newCustomer);
            customerAddress.setDefault(true);
            var newAddress = customerAddressRepository.save(customerAddress);
            List<CustomerAddress> customerAddresses = new ArrayList<>();
            customerAddresses.add(newAddress);
            newCustomer.setCustomerAddresses(customerAddresses);
        }

        log.debug("Created Information for Customer: {}", customer);
        return customerDTOMapper.apply(newCustomer);
    }

    public CustomerDTO updateCustomer(CustomerUpdateDTO request, Long customerId) {
        return Optional.of(customerRepository.findById(customerId)).filter(Optional::isPresent)
                .map(Optional::get).map(customer -> {
                    BeanUtils.copyProperties(request, customer);
                    customerRepository.save(customer);
                    log.debug("Changed Information for Customer: {}", customer);
                    return customer;
                }).map(customerDTOMapper).get();
    }

    public void deleteCustomers(Long[] ids) {
        for (Long id : ids) {
            customerRepository.findById(id).ifPresent(customer -> {
                customerRepository.delete(customer);
                log.debug("Deleted Customer: {}", customer);
            });
        }
    }

    public void deleteCustomer(Long id) throws ConstraintViolationException {
        customerRepository.findById(id).ifPresent(customer -> {
            customerRepository.delete(customer);
            log.debug("Deleted Customer: {}", customer);
        });
    }

    private void checkIfMobileAlreadyOrThrow(String mobile) {
        if (customerRepository.existsCustomerByMobile(mobile)) {
            throw new DuplicateResourceException("Số điện thoại đã được sử dụng");
        }
    }

    private void checkIfEmailExistsOrThrow(String email) {
        if (customerRepository.existsCustomerByEmail(email.toLowerCase())) {
            throw new DuplicateResourceException("Email này đã được sử dụng");
        }
    }

}
