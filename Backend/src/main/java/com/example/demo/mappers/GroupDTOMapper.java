package com.example.demo.mappers;

import com.example.demo.domain.dto.admin.group.GroupDTO;
import com.example.demo.domain.entities.Group;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GroupDTOMapper implements Function<Group, GroupDTO> {

    @Override
    public GroupDTO apply(Group t) {
        return new GroupDTO(t.getGroupName(), t.getGroupCode(), t.getCreatedDate(),t.getCreatedBy());
    }

}
