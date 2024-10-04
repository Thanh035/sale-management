package com.example.myapp.services;

import com.example.myapp.domain.dto.admin.customer.CustomerDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerDetailDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerRequestDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerUpdateDTO;
import com.example.myapp.exception.DuplicateResourceException;
import com.example.myapp.mappers.CustomerDTOMapper;
import com.example.myapp.domain.entities.Customer;
import com.example.myapp.domain.entities.CustomerAddress;
import com.example.myapp.repositories.*;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerDTOMapper customerDTOMapper;

    private final CustomerAddressRepository customerAddressRepository;

    private final ProvinceRepository provinceRepository;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

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

    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
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

            var province = provinceRepository.findByCode(request.getProvinceCode());
            var district = districtRepository.findByCode(request.getDistrictCode());
            var ward = wardRepository.findByCode(request.getWardCode());
            customerAddress.setProvince(province);
            customerAddress.setDistrict(district);
            customerAddress.setWard(ward);

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
