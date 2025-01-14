package com.example.demo.domain.dto.admin.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CustomerRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String mobile;
    private String email;

    private String address;
    private String phoneNumber;

    private String provinceCode;

    private String districtCode;

    private String wardCode;
}
