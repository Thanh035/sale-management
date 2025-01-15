package com.example.demo.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "tbl_province")
@NoArgsConstructor
@AllArgsConstructor
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String code;

    @Size(max = 255)
    @Column(length = 255)
    private String name;

}
