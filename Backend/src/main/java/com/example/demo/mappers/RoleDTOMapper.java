package com.example.demo.mappers;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.admin.role.RoleDTO;
import com.example.demo.domain.entities.Role;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleDTOMapper implements Function<Role, RoleDTO> {

	@Override
	public RoleDTO apply(Role t) {
		RoleDTO dto = new RoleDTO();
		BeanUtils.copyProperties(t, dto);
		return dto;
	}

}