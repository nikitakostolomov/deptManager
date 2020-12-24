package com.deptManager.deptManager.service;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.dto.DeptRequestDto;
import com.deptManager.deptManager.dto.PersonDto;
import com.deptManager.deptManager.exceptions.GeneralException;
import com.deptManager.deptManager.model.*;
import com.deptManager.deptManager.repositories.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeptService {

    private final DeptRepository deptRepository;

    private final CommonService commonService;

    private final GroupsService groupsService;

    private final PersonService personService;

    public Dept requestDept(UUID groupId, DeptRequestDto deptDto, Authentication authentication) {
        Person receiver = commonService.getPersonFromContext(authentication);
        groupsService.checkIfPersonIsMember(receiver, groupId);

        Person payer = personService.getById(deptDto.getPayerId());
        groupsService.checkIfPersonIsMember(payer, groupId);

        Groups group = groupsService.getById(groupId);
        Dept requestedDept = Dept.builder().amount(deptDto.getAmount())
                .comment(deptDto.getComment())
                .group(group)
                .payer(payer)
                .receiver(receiver)
                .deptStatus(Status.IN_PROGRESS)
                .build();
        return deptRepository.save(requestedDept);
    }

    public Dept approveDeptAsPayer(UUID deptId, Authentication authentication) {
        Person payer = commonService.getPersonFromContext(authentication);
        Dept dept = getDeptById(deptId);
        if (dept.getPayer().getId().equals(payer.getId())) {
            dept.setApprovedBySender(true);
            deptRepository.save(dept);
        } else {
            throw new GeneralException("You are not a payer of this dept");
        }
        return dept;
    }

    private Dept getDeptById(UUID deptId) {
        return deptRepository.findById(deptId)
                .orElseThrow(() -> new GeneralException("Dept not found"));
    }

    public Dept approveDeptAsReceiver(UUID deptId, Authentication authentication, boolean throwException) {
        Person receiver = commonService.getPersonFromContext(authentication);
        Dept dept = getDeptById(deptId);
        if (dept.getReceiver().getId().equals(receiver.getId())) {
            if (!dept.getApprovedBySender()) {
                if (throwException) {
                    throw new GeneralException("You can't approve before payer approves");
                }
                else return dept;
            }
            dept.setApprovedByReceiver(true);
            dept.setDeptStatus(Status.COMPLETE);
            deptRepository.save(dept);
        } else {

            throw new GeneralException("You are not a receiver of this dept");
        }
        return dept;
    }

    public List<Dept> filterDepts(UUID groupId, String status, Authentication authentication) {
        Groups group = groupsService.getGroupById(groupId, authentication);
        Status deptStatus = Status.valueOf(status.toUpperCase());
        List<Dept> collect = group.getDepts()
                .stream()
                .filter(dept -> dept.getDeptStatus().equals(deptStatus))
                .sorted(Comparator.comparing(Dept::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return collect;
    }

    public List<DeptsShorten> findAllDeptsByRoleForStats(UUID groupId, Authentication authentication, boolean isPayer) {
        Groups group = groupsService.getGroupById(groupId, authentication);
        Person personFromContext = commonService.getPersonFromContext(authentication);
        Map<Person, List<Dept>> personListMap;
        if (isPayer) {
            List<Dept> iAmPayer = group.getDepts()
                    .stream()
                    .filter(dept -> dept.getPayer().getId().equals(personFromContext.getId())
                            && dept.getDeptStatus().equals(Status.IN_PROGRESS))
                    .collect(Collectors.toList());

            personListMap = iAmPayer.stream().collect(Collectors.groupingBy(Dept::getReceiver));
        } else {
            List<Dept> iAmReceiver = group.getDepts()
                    .stream()
                    .filter(dept -> dept.getReceiver().getId().equals(personFromContext.getId())
                            && dept.getDeptStatus().equals(Status.IN_PROGRESS))
                    .collect(Collectors.toList());

            personListMap = iAmReceiver.stream().collect(Collectors.groupingBy(Dept::getPayer));
        }
        List<DeptsShorten> calculatedDepts = new ArrayList<>();
        for (Person person : personListMap.keySet()) {
            List<Dept> depts = personListMap.get(person);
            List<UUID> deptIds = depts.stream().map(Dept::getId).collect(Collectors.toList());
            int sum = depts.stream().map(Dept::getAmount).mapToInt(Integer::intValue).sum();

            calculatedDepts.add(
                    new DeptsShorten(DtoMapper.convertToClass(person, PersonDto.class), deptIds, sum, groupId));
        }
        return calculatedDepts;
    }

    public List<DeptsShorten> approveAllDeptsAsPayer(List<UUID> deptIds, UUID groupId, Authentication authentication) {
        deptIds.forEach(deptId -> approveDeptAsPayer(deptId, authentication));
        return findAllDeptsByRoleForStats(groupId, authentication, true);
    }

    public List<DeptsShorten> approveAllDeptsAsReceiver(List<UUID> deptIds, UUID groupId, Authentication authentication) {
        deptIds.forEach(deptId -> approveDeptAsReceiver(deptId, authentication, false));
        return findAllDeptsByRoleForStats(groupId, authentication, false);
    }

    public List<Dept> findAllDeptsByRole(UUID groupId, Authentication authentication, boolean isPayer, String status) {
        Groups group = groupsService.getGroupById(groupId, authentication);
        Person personFromContext = commonService.getPersonFromContext(authentication);
        Status deptStatus = Status.valueOf(status.toUpperCase());

        List<Dept> resultDepts;
        if (isPayer) {
            resultDepts = group.getDepts()
                    .stream()
                    .filter(dept -> dept.getPayer().getId().equals(personFromContext.getId())
                            && dept.getDeptStatus().equals(deptStatus))
                    .collect(Collectors.toList());
        } else {
            resultDepts = group.getDepts()
                    .stream()
                    .filter(dept -> dept.getReceiver().getId().equals(personFromContext.getId())
                            && dept.getDeptStatus().equals(deptStatus))
                    .collect(Collectors.toList());
        }
        return resultDepts;
    }
}
