package com.example.demo.controllers.admin;

import com.example.demo.constants.RolesConstants;
import com.example.demo.domain.dto.admin.collection.CollectionDTO;
import com.example.demo.domain.dto.admin.collection.CollectionDetailDTO;
import com.example.demo.domain.dto.admin.collection.CollectionRequestDTO;
import com.example.demo.services.CollectionService;
import com.example.demo.utils.HeaderUtil;
import com.example.demo.utils.PaginationUtil;
import com.example.demo.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/admin/collections")
@Slf4j
@RequiredArgsConstructor
public class CollectionController {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections
            .unmodifiableList(Arrays.asList("id", "name"));

    private final CollectionService collectionService;

    @Value("${application.name}")
    private String applicationName;

    @GetMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF
            + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<List<CollectionDTO>> getAllCollections(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable,
            @RequestParam(name = "filter", required = false) String filter) {
        log.debug("REST request to get all Collection for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<CollectionDTO> page = collectionService.getAllCollections(filter, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("{collectionId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF
            + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<CollectionDetailDTO> getCollectionById(@PathVariable Integer collectionId) {
        log.debug("REST request to get Collection : {}", collectionId);
        return ResponseUtil.wrapOrNotFound(collectionService.getCollectionById(collectionId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<CollectionDTO> createProduct(@Valid @RequestBody CollectionRequestDTO request)
            throws URISyntaxException {
        log.debug("REST request to save Product : {}", request);
        CollectionDTO newCollection = collectionService.createCollection(request);
        return ResponseEntity
                .created(new URI("/api/admin/collections/" + newCollection.getId())).headers(HeaderUtil
                        .createAlert(applicationName, "collectionManagement.created", newCollection.getName()))
                .body(newCollection);
    }

    @PutMapping("{collectionId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<CollectionDTO> updateCollection(@Valid @RequestBody CollectionRequestDTO updateRequest,
                                                          @PathVariable("collectionId") Integer collectionId) {
        log.debug("REST request to update Collection : {}", updateRequest);
        Optional<CollectionDTO> updatedCollection = collectionService.updateCollection(updateRequest, collectionId);
        return ResponseUtil.wrapOrNotFound(updatedCollection,
                HeaderUtil.createAlert(applicationName, "collectionManagement.updated", updateRequest.getName()));
    }

    @DeleteMapping("{collectionId}")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer collectionId) {
        log.debug("REST request to delete Collections");
        collectionService.deleteCollectionById(collectionId);
        return ResponseEntity.noContent().headers(
                        HeaderUtil.createAlert(applicationName, "collectionManagement.deleted", collectionId.toString()))
                .build();
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public void deleteCollections(@RequestBody Integer[] ids) {
        collectionService.deleteCollections(ids);
    }

    @PutMapping("{collectionId}/update-products")
    @PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
    public void updateProduct(@RequestBody Long[] productIds, @PathVariable("collectionId") Integer collectionId) {
        collectionService.updateProduct(productIds, collectionId);
    }

}
