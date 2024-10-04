package com.example.myapp.domain.dto.admin.user;

import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    private String fullname;
    private String phoneNumber;
    private Set<String> roles;

}
