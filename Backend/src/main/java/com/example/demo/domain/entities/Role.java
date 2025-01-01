package com.example.demo.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "tbl_role")
public class Role extends AbstractAuditingEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	private String name;

	@Size(max = 20)
	@Column(length = 20)
	private String code;

	private String note;

//	@JsonIgnore
//	@ManyToMany
//	@JoinTable(name = "tbl_role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = {
//			@JoinColumn(name = "permission_id", referencedColumnName = "id") })
//	@BatchSize(size = 20)
//	private List<Permission> permissions = new ArrayList<>();

}
