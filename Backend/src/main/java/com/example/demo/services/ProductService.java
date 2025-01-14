package com.example.demo.services;

import com.example.demo.domain.dto.admin.product.ProductDTO;
import com.example.demo.domain.dto.admin.product.ProductDetailDTO;
import com.example.demo.domain.dto.admin.product.ProductRequestDTO;
import com.example.demo.domain.dto.admin.product.SalesProductDTO;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mappers.ProductDTOMapper;
import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.Supplier;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.CollectionRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CollectionRepository collectionRepository;

    private final SupplierRepository supplierRepository;

    private final ProductDTOMapper productDTOMapper;

    private final StorageService storageService;

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return productRepository.findAllWithFilter(filter, pageable).map(productDTOMapper);
        } else {
            return productRepository.findAllByDeletedAtIsNull(pageable).map(productDTOMapper);
        }
    }

    @Transactional(readOnly = true)
    public List<SalesProductDTO> getPublicProducts() {
        return productRepository.findAll().stream().map(productDTOMapper::toSalesProductDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SalesProductDTO> getAvailableProducts() {
        return productRepository
                .findAllByQuantityGreaterThanOrAllowOrdersTrue(0)
                .stream()
                .map(productDTOMapper::toSalesProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductDetailDTO> getProductById(Long productId) {
        return productRepository.findByIdAndDeletedAtIsNull(productId).map(productDTOMapper::toProductDetailDTO);
    }

    @Transactional(readOnly = true)
    public List<String> getSuppliers() {
        return supplierRepository.findAll().stream().map(Supplier::getName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }

    public ProductDTO createProduct(ProductRequestDTO newProductDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(newProductDTO, product);

        product.setSupplier(createSupplier(newProductDTO.getSupplierName()));
        product.setCategory(createCategory(newProductDTO.getCategoryName()));

        productRepository.save(product);
        log.debug("Created Information For Product: {}", newProductDTO);
        return productDTOMapper.apply(product);
    }

    public Optional<ProductDTO> updateProduct(ProductRequestDTO updateRequest, Long productId) {
        return Optional.of(productRepository.findById(productId)).filter(Optional::isPresent).map(Optional::get)
                .map(product -> {
                    boolean changes = false;

                    if (updateRequest.getCategoryName() != null
                            && !updateRequest.getCategoryName().equals(product.getCategory().getName())) {
                        product.setCategory(createCategory(updateRequest.getCategoryName()));
                    }

                    if (updateRequest.getSupplierName() != null
                            && !updateRequest.getSupplierName().equals(product.getSupplier().getName())) {
                        product.setSupplier(createSupplier(updateRequest.getSupplierName()));
                    }

                    if (updateRequest.getProductName() != null
                            && !updateRequest.getProductName().equals(product.getProductName())) {
                        product.setProductName(updateRequest.getProductName());
                        changes = true;
                    }

                    String sku = updateRequest.getSku();
                    if (sku != null && !sku.equals(product.getSku())) {
                        product.setSku(sku);
                        changes = true;
                    }

                    String barcode = updateRequest.getBarcode();
                    if (barcode != null && !barcode.equals(product.getBarcode())) {
                        product.setBarcode(barcode);
                        changes = true;
                    }

                    if (updateRequest.getSellingPrice() != null
                            && product.getSellingPrice() != null
                            && updateRequest.getSellingPrice().compareTo(product.getSellingPrice()) != 0) {
                        product.setSellingPrice(updateRequest.getSellingPrice());
                        changes = true;
                    }

                    if (updateRequest.getCapitalPrice() != null
                            && product.getCapitalPrice() != null
                            && updateRequest.getCapitalPrice().compareTo(product.getCapitalPrice()) != 0) {
                        product.setCapitalPrice(updateRequest.getCapitalPrice());
                        changes = true;
                    }

                    if (updateRequest.getComparePrice() != null
                            && product.getComparePrice() != null
                            && updateRequest.getComparePrice().compareTo(product.getComparePrice()) != 0) {
                        product.setComparePrice(updateRequest.getComparePrice());
                        changes = true;
                    }

                    if (updateRequest.getQuantity() != null
                            && !updateRequest.getQuantity().equals(product.getQuantity())) {
                        product.setQuantity(updateRequest.getQuantity());
                        changes = true;
                    }


                    product.setDescription(updateRequest.getDescription());
                    changes = true;

                    if (updateRequest.getAllowOrders() != null
                            && !updateRequest.getAllowOrders().equals(product.getAllowOrders())) {
                        product.setAllowOrders(updateRequest.getAllowOrders());
                        changes = true;
                    }

                    if (!changes) {
                        throw new RequestValidationException("no data changes found");
                    }
                    log.debug("Changed Information for Product: {}", product);
                    return product;
                }).map(productDTOMapper);
    }

    private Category createCategory(String name) {
        if (name != null) {
            return categoryRepository.findById(name)
                    .orElseGet(() -> categoryRepository.saveAndFlush(new Category(name)));
        } else {
            return categoryRepository.findById("Khác")
                    .orElseGet(() -> categoryRepository.saveAndFlush(new Category("Khác")));
        }
    }

    private Supplier createSupplier(String name) {
        if (name != null) {
            return supplierRepository.findById(name)
                    .orElseGet(() -> supplierRepository.saveAndFlush(new Supplier(name)));
        }
        {
            return supplierRepository.findById("Khác")
                    .orElseGet(() -> supplierRepository.saveAndFlush(new Supplier("Khác")));
        }
    }

    public void deleteProducts(Long[] ids) {
        for (Long id : ids) {
            deleteProductById(id);
        }
    }

    public void deleteProductById(Long productId) {
        checkIfProductExistsOrThrow(productId);

        collectionRepository.findByProducts_Id(productId).forEach(collection -> {
            collection.getProducts().removeIf(product -> product.getId().equals(productId));
            collectionRepository.save(collection);
        });

        productRepository.findById(productId).ifPresent(product -> {
            product.getOrderDetails().forEach(orderDetail -> orderDetail.setProduct(null));
            productRepository.delete(product);
            log.debug("Deleted Product: {}", product);
        });

    }

    private void checkIfProductExistsOrThrow(Long productId) {
        if (!productRepository.existsByIdAndDeletedAtIsNull(productId)) {
            throw new ResourceNotFoundException(String.format("product with id [%s] not found", productId));
        }
    }

    public void uploadProductImage(Long productId, List<MultipartFile> files) {
        checkIfProductExistsOrThrow(productId);
       files.forEach(file -> {
           String imageUrl = UUID.randomUUID().toString();
           try {
               storageService.putObject("point-of-sale-management.appspot.com",
                       String.format("product-images/%s/%s", productId, imageUrl), file.getBytes());
           } catch (IOException e) {
               throw new RuntimeException("failed to upload product image", e);
           }
           productRepository.updateImageUrl(imageUrl, productId);
       });
    }

    public byte[] getProductImage(Long productId) {
        var product = productRepository.findByIdAndDeletedAtIsNull(productId).map(productDTOMapper).orElseThrow(
                () -> new ResourceNotFoundException(String.format("product with id [%s] not found", productId)));

        if (StringUtils.isBlank(product.getProductImageId())) {
            throw new ResourceNotFoundException(
                    String.format("product with id [%s] product image not found", productId));
        }

        byte[] productImage = storageService.getObject("point-of-sale-management.appspot.com",
                String.format("product-images/%s/%s", productId, product.getProductImageId()));
        return productImage;
    }

    public void deleteProductImage(Long productId) {
        var product = productRepository.findByIdAndDeletedAtIsNull(productId).map(productDTOMapper).orElseThrow(
                () -> new ResourceNotFoundException(String.format("product with id [%s] not found", productId)));

        if (StringUtils.isBlank(product.getProductImageId())) {
            throw new ResourceNotFoundException(
                    String.format("product with id [%s] product image not found", productId));
        }

        storageService.deleteObject("point-of-sale-management.appspot.com",
                String.format("product-images/%s/%s", productId, product.getProductImageId()));
        productRepository.updateImageUrl(null, productId);
    }

    public List<ProductDTO> getProducts() {
        return productRepository.findAllByDeletedAtIsNull().stream().map(productDTOMapper)
                .collect(Collectors.toList());
    }

//	public List<SalesProductDTO> getProductsByCategoryId(Integer categoryId) {
//		return categoryRepository.findOneByIdAndDeletedAtIsNull(categoryId)
//				.map(productRepository::findAllByCategoryAndDeletedAtIsNull).orElseGet(ArrayList::new).stream()
//				.map(productDTOMapper::toSalesProductDTO).collect(Collectors.toList());
//	}

}
