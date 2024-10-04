package com.example.myapp.controllers.admin;

import com.example.myapp.constants.RolesConstants;
import com.example.myapp.domain.dto.admin.customer.CustomerDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerDetailDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerRequestDTO;
import com.example.myapp.domain.dto.admin.customer.CustomerUpdateDTO;
import com.example.myapp.services.CustomerService;
import com.example.myapp.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/admin/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public List<CustomerDTO> getAllCustomers(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                             @RequestParam(name = "filter", required = false) String filter) {
        log.debug("REST request to get all Customer for an admin");
        Page<CustomerDTO> page = customerService.getAllCustomers(filter, pageable);
        return page.getContent();
    }

    @GetMapping("{customerId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<CustomerDetailDTO> getCustomerById(@PathVariable Long customerId) {
        log.debug("REST request to get Customer : {}", customerId);
        return ResponseUtil.wrapOrNotFound(customerService.getCustomer(customerId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerRequestDTO request) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", request);
        CustomerDTO newCustomer = customerService.createCustomer(request);
        return newCustomer;
    }

    @PutMapping("{customerId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public CustomerDTO updateCustomer(@Valid @RequestBody CustomerUpdateDTO updateRequest,
                                      @PathVariable("customerId") Long customerId) {
        log.debug("REST request to update Customer : {}", updateRequest);
        return customerService.updateCustomer(updateRequest, customerId);
    }

//    @DeleteMapping("{customerId}")
//    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
//        log.debug("REST request to delete Customer");
//        customerService.deleteCustomerById(customerId);
//        return ResponseEntity.noContent().headers(
//                        HeaderUtil.createAlert(applicationName, "collectionManagement.deleted", collectionId.toString()))
//                .build();
//    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public void deleteCustomers(@RequestBody Long[] ids) {
        customerService.deleteCustomers(ids);
    }
}