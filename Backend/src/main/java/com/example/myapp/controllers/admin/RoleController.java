package com.example.myapp.controllers.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.constants.RolesConstants;
import com.example.myapp.domain.dto.admin.role.RoleDTO;
import com.example.myapp.services.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1.0/admin/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

	private final RoleService roleService;

	@GetMapping
	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
	public List<RoleDTO> getAllRoles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get all Collection for an admin");
		Page<RoleDTO> page = roleService.getAllManagedRoles(pageable);
		return page.getContent();
	}

}
