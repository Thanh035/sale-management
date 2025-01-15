package com.example.demo.controllers.admin;

import com.example.demo.constants.RolesConstants;
import com.example.demo.domain.dto.admin.order.DraftOrderDTO;
import com.example.demo.domain.dto.admin.order.DraftOrderRequestDTO;
import com.example.demo.domain.dto.admin.order.DraftOrderViewDTO;
import com.example.demo.services.DraftOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/admin/draftOrders")
@RequiredArgsConstructor
@Slf4j
public class DraftOrderController {

    private final DraftOrderService draftOrderService;

    @Value("${application.name}")
    private String applicationName;

    @GetMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public List<DraftOrderDTO> getAllDraftOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        log.debug("REST request to get all Draft Orders for an admin");

        Page<DraftOrderDTO> page = draftOrderService.getAllDraftOrders(filter, pageable);
        return page.getContent();
    }

    @GetMapping("{draftOrderId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public DraftOrderViewDTO getOrder(@PathVariable Long draftOrderId) {
        log.debug("REST request to get draft Order : {}", draftOrderId);
        return draftOrderService.getDraftOrderWithOrderDetailsById(draftOrderId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public DraftOrderDTO createDraftOrder(@Valid @RequestBody DraftOrderRequestDTO request) throws URISyntaxException {
        log.debug("REST request to save Draft Order : {}", request);
        return draftOrderService.createDraftOrder(request);
    }

//	@PutMapping("{draftOrderId}")
//	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
//	public DraftOrderDTO updateDraftOrder(@Valid @RequestBody DraftOrderRequestDTO updateRequest,
//			@PathVariable("draftOrderId") Long draftOrderId) {
//		log.debug("REST request to update User : {}", updateRequest);
//		return draftOrderService.updateDraftOrder(updateRequest, draftOrderId).get();
//	}

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public void deleteDraftOrder(@PathVariable Long id) {
        log.debug("REST request to delete Draft Orders");
        draftOrderService.deleteDraftOrderById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public void deleteDraftOrders(@RequestBody Long[] ids) {
        draftOrderService.deleteDraftOrders(ids);
    }

}
