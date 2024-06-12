package com.example.myapp.mapper;

import com.example.myapp.dto.admin.account.AccountDTO;
import com.example.myapp.dto.admin.user.UserDTO;
import com.example.myapp.dto.admin.user.UserDetailDTO;
import com.example.myapp.model.Role;
import com.example.myapp.model.User;
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
