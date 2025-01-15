package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.dto.admin.group.GroupDTO;
import com.example.demo.mappers.GroupDTOMapper;
import com.example.demo.repositories.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GroupService {

	private final GroupRepository groupRepository;

	private final GroupDTOMapper groupDTOMapper;

	@Transactional(readOnly = true)
	public Page<GroupDTO> getAllManagedRoles(Pageable pageable) {
		return groupRepository.findAll(pageable).map(groupDTOMapper);
	}

}
