package com.deptManager.deptManager.service;

import com.deptManager.deptManager.exceptions.GeneralException;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupsService {

    private final GroupsRepository groupsRepository;

    private final GroupPersonLinkRepository groupPersonLinkRepository;

    private final PersonService personService;

    private final CommonService commonService;

    public Groups createGroup(String name, Authentication authentication) {
        Person person = commonService.getPersonFromContext(authentication);

        Groups group = new Groups();
        group.setName(name);
        Groups savedGroup = groupsRepository.save(group);

        addToGroup(person, savedGroup, PersonRole.ROLE_ADMIN);
        return savedGroup;
    }

    private void addToGroup(Person person, Groups group, PersonRole personRole) {
        GroupPersonLink groupPersonLink = new GroupPersonLink();
        groupPersonLink.setGroup(group);
        groupPersonLink.setPerson(person);
        groupPersonLink.setPersonRole(personRole);
        groupPersonLinkRepository.save(groupPersonLink);

        group.getParticipantsList().add(groupPersonLink);
        person.getGroupsList().add(groupPersonLink);
    }

    public Groups addPersonToGroup(UUID groupId, UUID personId, Authentication authentication) {
        Person requester = commonService.getPersonFromContext(authentication);
        checkIfPersonIsAdminAtGroup(requester, groupId);
        Person personToAdd = personService.getById(personId);
        Groups groupsWhereToAdd = getById(groupId);
        checkIfPersonIsAlreadyAMemberAtGroup(personToAdd, groupId);
        addToGroup(personToAdd, groupsWhereToAdd, PersonRole.ROLE_USER);
        return groupsWhereToAdd;
    }

    private void checkIfPersonIsAdminAtGroup(Person person, UUID groupId) {
        checkIfPersonIsMember(person, groupId);
        GroupPersonLink groupPersonLink = getPersonGroupLink(person, groupId).get();

        if (!groupPersonLink.getPersonRole().equals(PersonRole.ROLE_ADMIN)) {
            throw new GeneralException("You are not admin in this group");
        }
    }

    private boolean checkIfPersonIsNotAdminAtGroup(Person person, UUID groupId) {
        checkIfPersonIsMember(person, groupId);
        GroupPersonLink groupPersonLink = getPersonGroupLink(person, groupId).get();

        return groupPersonLink.getPersonRole().equals(PersonRole.ROLE_ADMIN);

    }

    private Optional<GroupPersonLink> getPersonGroupLink(Person person, UUID groupId) {
        return groupPersonLinkRepository.findById(GroupPersonLinkKey.builder()
                .personId(person.getId())
                .groupId(groupId)
                .build());
    }

    public void checkIfPersonIsMember(Person person, UUID groupId) {
        if (!getPersonGroupLink(person, groupId).isPresent()) {
            throw new GeneralException("Person is not a member of the group!");
        }
    }

    private void checkIfPersonIsAlreadyAMemberAtGroup(Person person, UUID groupId) {
        getPersonGroupLink(person, groupId).ifPresent(per -> {
            throw new GeneralException("Person is already a member of the group!");
        });
    }

    public Groups getById(UUID id) {
        return groupsRepository.findById(id).orElseThrow(() -> new GeneralException("Group not found"));
    }

    public void deleteGroup(UUID groupId, Authentication authentication) {
        Person requester = commonService.getPersonFromContext(authentication);
        checkIfPersonIsAdminAtGroup(requester, groupId);
        Groups groupToDelete = getById(groupId);
        groupToDelete.getParticipantsList()
                .stream()
                .map(GroupPersonLink::getPerson)
                .forEach(participant -> {
                    List<GroupPersonLink> beforeDelete = participant.getGroupsList();
                    List<GroupPersonLink> afterDelete = beforeDelete.stream()
                            .filter(link -> !link.getGroup().getId().equals(groupId))
                            .collect(Collectors.toList());
                    participant.setGroupsList(afterDelete);
                });

        groupsRepository.deleteById(groupId);

    }

    public Groups renameGroup(UUID groupId, String newName, Authentication authentication) {
        Person requester = commonService.getPersonFromContext(authentication);
        checkIfPersonIsAdminAtGroup(requester, groupId);
        Groups groups = getById(groupId);
        groups.setName(newName);
        groupsRepository.save(groups);
        return groups;
    }

    public Groups getGroupById(UUID groupId, Authentication authentication) {
        Person requester = commonService.getPersonFromContext(authentication);
        checkIfPersonIsMember(requester, groupId);
        return getById(groupId);
    }

    // TODO: refactor when do dept functionality
    public Groups deletePersonFromGroup(UUID groupId, UUID personId, Authentication authentication) {
        Person requester = commonService.getPersonFromContext(authentication);
        checkIfPersonIsAdminAtGroup(requester, groupId);
        Groups groups = getById(groupId);
        Person personToDelete = personService.getById(personId);
        if (checkIfPersonIsNotAdminAtGroup(personToDelete, groupId)) {
            throw new GeneralException("You cannot delete admin");
        }
        List<GroupPersonLink> before = personToDelete.getGroupsList();
        List<GroupPersonLink> afterDelete = before.stream()
                .filter(link -> !link.getGroup().getId().equals(groupId))
                .collect(Collectors.toList());
        personToDelete.setGroupsList(afterDelete);

        List<GroupPersonLink> beforeGroup = groups.getParticipantsList();
        List<GroupPersonLink> afterDeleteGroup = beforeGroup.stream()
                .filter(link -> !link.getPerson().getId().equals(personId))
                .collect(Collectors.toList());
        groups.setParticipantsList(afterDeleteGroup);
        groupPersonLinkRepository.deleteById(GroupPersonLinkKey.builder()
                .personId(personId)
                .groupId(groupId)
                .build());

        return groups;
    }
}
