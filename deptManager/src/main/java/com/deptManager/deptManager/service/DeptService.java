package com.deptManager.deptManager.service;

import com.deptManager.deptManager.dto.DeptRequestDto;
import com.deptManager.deptManager.exceptions.GeneralException;
import com.deptManager.deptManager.model.Dept;
import com.deptManager.deptManager.model.Groups;
import com.deptManager.deptManager.model.Person;
import com.deptManager.deptManager.repositories.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeptService {

    private final DeptRepository deptRepository;

    private final CommonService commonService;

    private final GroupsService groupsService;

    private final PersonService personService;

    public Dept requestDept(UUID groupId, DeptRequestDto deptDto, Authentication authentication){
        Person receiver = commonService.getPersonFromContext(authentication);
        groupsService.checkIfPersonIsMember(receiver, groupId);

        Person payer = personService.getById(deptDto.getReceiver());
        groupsService.checkIfPersonIsMember(payer, groupId);

        Groups group = groupsService.getById(groupId);
        Dept requestedDept = Dept.builder().amount(deptDto.getAmount())
                .comment(deptDto.getComment())
                .group(group)
                .payer(payer)
                .receiver(receiver)
                .build();
        return deptRepository.save(requestedDept);
    }

    public Dept approveDeptAsPayer(UUID deptId, Authentication authentication){
        Person payer = commonService.getPersonFromContext(authentication);
        Dept dept = getDeptById(deptId);
        if (dept.getPayer().getId().equals(payer.getId())){
            dept.setApprovedBySender(true);
            deptRepository.save(dept);
        } else {
            throw new GeneralException("You are not a payer of this dept");
        }
        return dept;
    }

    private Dept getDeptById(UUID deptId){
       return deptRepository.findById(deptId)
               .orElseThrow(()-> new GeneralException("Dept not found"));
    }

    public Dept approveDeptAsReceiver(UUID deptId, Authentication authentication) {
        Person receiver = commonService.getPersonFromContext(authentication);
        Dept dept = getDeptById(deptId);
        if (dept.getReceiver().getId().equals(receiver.getId())){
            if (!dept.getApprovedBySender()){
                throw new GeneralException("You can't approve before payer approves");
            }
            dept.setApprovedByReceiver(true);
            deptRepository.save(dept);
        } else {
            throw new GeneralException("You are not a receiver of this dept");
        }
        return dept;
    }
}
