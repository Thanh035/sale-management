package com.example.demo.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_collection")
public class Collection extends AbstractAuditingEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	private String name;

	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	private String code;

	@Column(columnDefinition = "TEXT")
	private String description;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
		name = "tbl_collection_product", joinColumns = @JoinColumn(name = "collection_id"), 
		inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	@BatchSize(size = 20)
	private List<Product> products;

}
