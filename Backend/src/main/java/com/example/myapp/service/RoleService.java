package com.example.myapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.dto.admin.role.RoleDTO;
import com.example.myapp.mapper.RoleDTOMapper;
import com.example.myapp.repository.RoleRepository;

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
