package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "tbl_customer_address")
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullName;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_code", referencedColumnName = "code")
    private District district;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ward_code", referencedColumnName = "code")
    private Ward ward;

}
