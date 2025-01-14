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
public class CustomerUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String fullName;
    private String mobile;
}