package com.deptManager.deptManager.controller;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.dto.GroupDto;
import com.deptManager.deptManager.dto.GroupPersonLinkDto;
import com.deptManager.deptManager.service.GroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.GROUPS)
public class GroupsController {

    private final GroupsService groupsService;

    @PostMapping
    public GroupDto createNewGroup(@RequestParam String name, Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.createGroup(name, authentication), GroupDto.class);
    }

    @GetMapping
    public List<GroupDto> getAllGroupsWhereAdmin(Authentication authentication){
        return DtoMapper.convertList(groupsService.getAllGroupsWhereAdmin(authentication), GroupDto.class);
    }

    @PostMapping(RequestInfo.GROUP_ID)
    public GroupDto addUserToGroup(@PathVariable UUID groupId, @RequestParam UUID personId,
                                   Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.addPersonToGroup(groupId, personId, authentication), GroupDto.class);
    }

    @GetMapping(RequestInfo.GROUP_ID)
    public GroupDto getGroup(@PathVariable UUID groupId,
                                   Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.getGroupById(groupId, authentication), GroupDto.class);
    }

    @GetMapping(RequestInfo.GROUP_ID + RequestInfo.PARTICIPANTS)
    public List<GroupPersonLinkDto> getGroupParticipants(@PathVariable UUID groupId,
                                                         Authentication authentication) {
        return DtoMapper.convertList(groupsService.getGroupParticipants(groupId, authentication), GroupPersonLinkDto.class);
    }

    @DeleteMapping(RequestInfo.GROUP_ID)
    public void deleteGroup(@PathVariable UUID groupId,
                                   Authentication authentication) {
        groupsService.deleteGroup(groupId, authentication);
    }

    @DeleteMapping(RequestInfo.GROUP_ID + RequestInfo.PERSON)
    public GroupDto deletePersonFromGroup(@PathVariable UUID groupId, @RequestParam UUID personId,
                            Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.deletePersonFromGroup(groupId, personId, authentication), GroupDto.class);
    }

    @PutMapping(RequestInfo.GROUP_ID)
    public GroupDto renameGroup(@PathVariable UUID groupId, @RequestParam String newName,
                            Authentication authentication) {
        return DtoMapper.convertToClass(groupsService.renameGroup(groupId, newName, authentication), GroupDto.class);
    }
}
