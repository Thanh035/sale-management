package com.example.myapp.mappers;

import com.example.myapp.domain.dto.admin.account.AccountDTO;
import com.example.myapp.domain.dto.admin.user.UserDTO;
import com.example.myapp.domain.dto.admin.user.UserDetailDTO;
import com.example.myapp.domain.entities.Role;
import com.example.myapp.domain.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setRoles(user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()));
        return dto;
    }

    public AccountDTO toAccountDTO(User user) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setRoles(user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()));
        return dto;
    }

    public UserDetailDTO toUserDetailDTO(User user) {
        UserDetailDTO dto = new UserDetailDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setRoles(user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()));
        return dto;
    }

}
