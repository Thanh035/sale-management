package com.example.myapp.domain.dto.admin.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CustomerDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String address;

    private String email;

    private String mobile;

    private String provinceName;

    private String districtName;

    private String wardName;

    private String phoneNumber;
}
