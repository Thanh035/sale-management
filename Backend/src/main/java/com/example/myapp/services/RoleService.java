package com.example.myapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.domain.dto.admin.role.RoleDTO;
import com.example.myapp.mappers.RoleDTOMapper;
import com.example.myapp.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleService {

	private final RoleRepository roleRepository;

	private final RoleDTOMapper roleDTOMapper;

	@Transactional(readOnly = true)
	public Page<RoleDTO> getAllManagedRoles(Pageable pageable) {
		return roleRepository.findAll(pageable).map(roleDTOMapper);
	}

}
