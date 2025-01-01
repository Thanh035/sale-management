package com.example.demo.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_order")
public class Order extends AbstractAuditingEntity<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String code;

	@Column(columnDefinition = "TEXT")
	private String note;
	
	@Column
	private boolean status;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "paying_amount")
	private BigDecimal payingAmount;
	
	@Column(name = "paid_date")
	private Instant paidDate;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

}
