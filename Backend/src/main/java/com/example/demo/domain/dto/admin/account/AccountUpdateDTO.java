package com.example.demo.domain.dto.admin.account;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    private String fullname;

    @Size(min = 9, max = 11)
    private String phoneNumber;

}
