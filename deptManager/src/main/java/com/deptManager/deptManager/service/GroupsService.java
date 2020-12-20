package com.deptManager.deptManager.service;

import com.deptManager.deptManager.common.AuthHelper;
import com.deptManager.deptManager.model.GroupPersonLink;
import com.deptManager.deptManager.model.Groups;
import com.deptManager.deptManager.model.Person;
import com.deptManager.deptManager.model.PersonRole;
import com.deptManager.deptManager.model.compositeKeys.GroupPersonLinkKey;
import com.deptManager.deptManager.repositories.GroupPersonLinkRepository;
import com.deptManager.deptManager.repositories.GroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupsService {

    private final GroupsRepository groupsRepository;

    private final GroupPersonLinkRepository groupPersonLinkRepository;

    private final PersonService personService;

    public Groups createGroup(String name, Authentication authentication) {
        String login = AuthHelper.getUsernameFromAuth(authentication);
        Person person = personService.getByLogin(login);

        Groups group = new Groups();
        group.setName(name);
        Groups savedGroup = groupsRepository.save(group);

        GroupPersonLink groupPersonLink = new GroupPersonLink();
        groupPersonLink.setGroup(savedGroup);
        groupPersonLink.setPerson(person);
        groupPersonLink.setPersonRole(PersonRole.ROLE_ADMIN);
        groupPersonLinkRepository.save(groupPersonLink);

        group.getParticipantsList().add(groupPersonLink);
        person.getGroupsList().add(groupPersonLink);
        return savedGroup;
    }
}
