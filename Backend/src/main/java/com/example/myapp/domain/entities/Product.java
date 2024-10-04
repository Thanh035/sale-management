package com.example.myapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_product")
public class Product extends AbstractAuditingEntity<Long> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 256)
    @Column(name = "product_image_id", length = 256)
    private String productImageId;

    @Size(max = 100)
    @Column(name = "product_name", length = 100)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    private String sku;
    private String barcode;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice = BigDecimal.ZERO;
    @Column(name = "compare_price")
    private BigDecimal comparePrice = BigDecimal.ZERO;
    @Column(name = "capital_price", nullable = false)
    private BigDecimal capitalPrice = BigDecimal.ZERO;
    @Column(name = "allow_orders", nullable = false)
    private Boolean allowOrders = false;
    @Column(nullable = false)
    private Integer quantity = 0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_name", referencedColumnName = "name")
    private Supplier supplier;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name", referencedColumnName = "name")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Collection> collections;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<UsingImage> usingImages = new ArrayList<>();
}
