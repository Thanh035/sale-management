package com.example.myapp.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.myapp.constant.RolesConstants;
import com.example.myapp.dto.admin.product.ProductDTO;
import com.example.myapp.dto.admin.product.ProductDetailDTO;
import com.example.myapp.dto.admin.product.ProductRequestDTO;
import com.example.myapp.service.ProductService;
import com.example.myapp.util.HeaderUtil;
import com.example.myapp.util.PaginationUtil;
import com.example.myapp.util.ResponseUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1.0/admin/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

	private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(Arrays.asList("id","productName","quantity"));

	private final ProductService productService;

	@Value("${application.name}")
	private String applicationName;

	@GetMapping
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.STAFF + "') or hasAuthority('" + RolesConstants.MANAGER + "')" )
	public ResponseEntity<List<ProductDTO>> getAllProducts(
			@org.springdoc.api.annotations.ParameterObject Pageable pageable,
			@RequestParam(name = "filter", required = false) String filter) {
		log.debug("REST request to get all Category for an admin");
		if (!onlyContainsAllowedProperties(pageable)) {
			return ResponseEntity.badRequest().build();
		}

		final Page<ProductDTO> page = productService.getAllProducts(filter, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("{productId}")
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable Long productId) {
		log.debug("REST request to get Product : {}", productId);
		return ResponseUtil.wrapOrNotFound(productService.getProductById(productId));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequestDTO request)
			throws URISyntaxException {
		log.debug("REST request to save Product : {}", request);
		ProductDTO newProduct = productService.createProduct(request);
		return ResponseEntity
				.created(new URI("/api/admin/products/" + newProduct.getId())).headers(HeaderUtil
						.createAlert(applicationName, "productManagement.created", newProduct.getProductName()))
				.body(newProduct);
	}

	@PutMapping("{productId}")
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductRequestDTO updateRequest,
			@PathVariable("productId") Long productId) {
		log.debug("REST request to update Product : {}", updateRequest);
		Optional<ProductDTO> updatedProduct = productService.updateProduct(updateRequest, productId);
		return ResponseUtil.wrapOrNotFound(updatedProduct,
				HeaderUtil.createAlert(applicationName, "productManagement.updated", updateRequest.getProductName()));
	}

	@DeleteMapping("{productId}")
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		log.debug("REST request to delete Categories");
		productService.deleteProductById(productId);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createAlert(applicationName, "productManagement.deleted", productId.toString()))
				.build();
	}

	private boolean onlyContainsAllowedProperties(Pageable pageable) {
		return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public void deleteProducts(@RequestBody Long[] ids) {
		productService.deleteProducts(ids);
	}

	@PostMapping(value = "{productId}/product-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAuthority('" + RolesConstants.ADMIN + "') or hasAuthority('" + RolesConstants.MANAGER + "')")
	public void uploadProductImage(@PathVariable("productId") Long productId,
			@RequestParam("file") MultipartFile file) {
		productService.uploadProductImage(productId, file);

	}

	@GetMapping(value = "{productId}/product-image", produces = MediaType.IMAGE_JPEG_VALUE)
	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
	public byte[] getProductImage(@PathVariable("productId") Long productId) {
		return productService.getProductImage(productId);
	}

	@DeleteMapping(value = "{productId}/product-image", produces = MediaType.IMAGE_JPEG_VALUE)
	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
	public void deleteProductImage(@PathVariable("productId") Long productId) {
		productService.deleteProductImage(productId);
	}

	@GetMapping("byOrder")
	public List<ProductDTO> getProducts() {
		return productService.getProducts();
	}

}
