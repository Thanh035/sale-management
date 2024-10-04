package com.example.myapp.domain.dto.admin.account;


import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Instant SignInAt;

    private String signInBrowser;

    private String signInIp;
}

