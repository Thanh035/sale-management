package com.example.myapp.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
