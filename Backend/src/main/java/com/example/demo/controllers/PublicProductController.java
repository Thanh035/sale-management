package com.example.demo.controllers;

import com.example.demo.domain.dto.admin.product.SalesProductDTO;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class PublicProductController {

    @Value("${application.name}")
    private String applicationName;

    private final ProductService productService;

//	@GetMapping("/products/byCategory")
//	public List<SalesProductDTO> getProductsByCategoryId(@RequestParam("categoryId") Integer categoryId) {
//		return productService.getProductsByCategoryId(categoryId);
//	}

    @GetMapping("/products")
    public List<SalesProductDTO> getPublicProducts() {
        return productService.getPublicProducts();
    }

    @GetMapping("/products/available")
    public List<SalesProductDTO> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return productService.getCategories();
    }

    @GetMapping("/suppliers")
    public List<String> getSuppliers() {
        return productService.getSuppliers();
    }

//	@GetMapping("{productId}")
//	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
//	public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable Long productId) {
//		log.debug("REST request to get Product : {}", productId);
//		return ResponseUtil.wrapOrNotFound(productService.getProductById(productId));
//	}

    @GetMapping(value = "{productId}/product-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProductImage(@PathVariable("productId") Long productId) {
        return productService.getProductImage(productId);
    }

}
