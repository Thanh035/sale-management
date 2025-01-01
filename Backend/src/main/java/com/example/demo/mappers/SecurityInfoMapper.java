package com.example.demo.mappers;


import com.example.demo.domain.dto.admin.account.LoginHistoryDTO;
import com.example.demo.domain.dto.admin.account.SecurityInfoDTO;
import com.example.demo.domain.entities.LoginHistory;
import com.example.demo.domain.entities.Role;
import com.example.demo.domain.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityInfoMapper implements Function<User,SecurityInfoDTO> {
    private LoginHistoryDTO toLoginHistoryDTO(LoginHistory loginHistory) {
        LoginHistoryDTO dto = new LoginHistoryDTO();
        BeanUtils.copyProperties(loginHistory, dto);
        return dto;
    }

    @Override
    public SecurityInfoDTO apply(User user) {
        SecurityInfoDTO dto = new SecurityInfoDTO();
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        if (user.getLoginHistories() != null) {
            List<LoginHistoryDTO> loginHistories = new ArrayList<>();
            user.getLoginHistories().stream().forEach(loginHistory -> {
                loginHistories.add(toLoginHistoryDTO(loginHistory));
            });
            dto.setLoginHistories(loginHistories);
        }
        return dto;
    }
}
