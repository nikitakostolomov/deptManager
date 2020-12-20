package com.deptManager.deptManager.controller;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.dto.GroupDto;
import com.deptManager.deptManager.service.GroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.GROUPS)
public class GroupsController {

    private final GroupsService groupsService;

    @PostMapping
    public GroupDto createNewGroup(@RequestParam String name, Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.createGroup(name, authentication), GroupDto.class);
    }
}
