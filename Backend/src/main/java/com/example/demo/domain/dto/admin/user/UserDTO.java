package com.example.demo.domain.dto.admin.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String login;

    private Long id;

    @Size(max = 100)
    private String fullname;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = false;

    // @Size(max = 256)
//	private String profileImageId;
    private Set<String> roles;

    private Instant createdDate;

}
