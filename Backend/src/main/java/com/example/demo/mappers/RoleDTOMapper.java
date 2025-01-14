package com.example.demo.mappers;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.admin.role.RoleDTO;
import com.example.demo.domain.entities.Group;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleDTOMapper implements Function<Group, RoleDTO> {

	@Override
	public RoleDTO apply(Group t) {
		RoleDTO dto = new RoleDTO();
		BeanUtils.copyProperties(t, dto);
		return dto;
	}

}
