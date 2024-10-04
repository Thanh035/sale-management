package com.example.myapp.domain.dto.admin.account;


import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityInfoDTO implements Serializable {
    private Set<String> roles;
    private List<LoginHistoryDTO> loginHistories;
}

