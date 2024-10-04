package com.example.myapp.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	private String name;

}