package com.deptManager.deptManager.controller;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.dto.GroupDto;
import com.deptManager.deptManager.dto.PersonDto;
import com.deptManager.deptManager.model.Groups;
import com.deptManager.deptManager.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.PERSON)
public class PersonController {

    private final PersonService personService;

    @GetMapping(RequestInfo.GROUPS)
    public List<GroupDto> getAllGroupsOfUser(Authentication authentication) {
        List<Groups> allGroupsOfUser = personService.getAllGroupsOfUser(authentication);
        for (Groups group : allGroupsOfUser){
            System.out.println(group.getParticipantsList());
        }
        return DtoMapper.convertList(allGroupsOfUser, GroupDto.class);
    }

    @GetMapping()
    public List<PersonDto> searchByLogin(@RequestParam (defaultValue = RequestInfo.DEFAULT_SEARCH_VALUE)
                                                     String login){
        return DtoMapper.convertList(personService.searchByLogin(login), PersonDto.class);
    }

    @GetMapping(RequestInfo.ACCOUNT)
    public PersonDto getPersonAccount(Authentication authentication){
        return DtoMapper.convertToClass(personService.getPersonAccount(authentication), PersonDto.class);
    }

}
