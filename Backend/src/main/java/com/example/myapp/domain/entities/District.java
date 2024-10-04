package com.example.myapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "tbl_district")
@NoArgsConstructor
@AllArgsConstructor
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String code;
    @Size(max = 255)
    @Column(length = 255)
    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;

}

