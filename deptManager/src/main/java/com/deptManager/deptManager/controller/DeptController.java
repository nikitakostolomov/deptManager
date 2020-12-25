package com.deptManager.deptManager.controller;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.dto.DeptDto;
import com.deptManager.deptManager.dto.DeptRequestDto;
import com.deptManager.deptManager.model.DeptsShorten;
import com.deptManager.deptManager.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.DEPT)
public class DeptController {

    private final DeptService deptService;

    @PostMapping(RequestInfo.GROUP_ID)
    public DeptDto requestDept(@PathVariable UUID groupId, @RequestBody DeptRequestDto deptRequestDto,
                               Authentication authentication) {
        return DtoMapper.convertToClass(deptService.requestDept(groupId, deptRequestDto, authentication), DeptDto.class);
    }

    @PostMapping(RequestInfo.DEPT_ID + RequestInfo.APPROVE + RequestInfo.PAYER)
    public DeptDto approveDeptAsPayer(@PathVariable UUID deptId, Authentication authentication) {
        return DtoMapper.convertToClass(deptService.approveDeptAsPayer(deptId, authentication), DeptDto.class);
    }

    @PostMapping(RequestInfo.DEPT_ID + RequestInfo.APPROVE + RequestInfo.RECEIVER)
    public DeptDto approveDeptAsReceiver(@PathVariable UUID deptId, Authentication authentication) {
        return DtoMapper.convertToClass(deptService.approveDeptAsReceiver(deptId, authentication, true), DeptDto.class);
    }

    @GetMapping(RequestInfo.GROUP_ID)
    public List<DeptDto> filterDeptsInGroup(@PathVariable UUID groupId, @RequestParam String status,
                                            Authentication authentication) {
        return DtoMapper.convertList(deptService.filterDepts(groupId, status, authentication), DeptDto.class);
    }

    @GetMapping( RequestInfo.STATISTICS + RequestInfo.PAYER)
    public List<DeptsShorten> findAllDeptsWherePayerStats(
                                                          Authentication authentication) {
        return deptService.findAllDeptsInAllGroups(authentication, true);
    }

    @GetMapping(RequestInfo.STATISTICS + RequestInfo.RECEIVER)
    public List<DeptsShorten> findAllDeptsWhereReceiverStats(
                                                             Authentication authentication) {
        return deptService.findAllDeptsInAllGroups(authentication, false);
    }

    @PostMapping(RequestInfo.APPROVE + RequestInfo.STATISTICS + RequestInfo.PAYER)
    public List<DeptsShorten> approveAllDeptsAsPayer(@RequestBody List<UUID> deptIds, Authentication authentication) {
        return deptService.approveAllDeptsAsPayer(deptIds, authentication);
    }

    @PostMapping( RequestInfo.APPROVE + RequestInfo.STATISTICS + RequestInfo.RECEIVER)
    public List<DeptsShorten> approveAllDeptsAsReceiver(@RequestBody List<UUID> deptIds, Authentication authentication) {
        return deptService.approveAllDeptsAsReceiver(deptIds, authentication);
    }


    @GetMapping(RequestInfo.GROUP_ID + RequestInfo.PAYER)
    public List<DeptDto> findAllDeptsWherePayer(@PathVariable UUID groupId, @RequestParam String status,
                                                Authentication authentication) {
        return DtoMapper.convertList(deptService.findAllDeptsByRole(groupId, authentication, true, status), DeptDto.class);
    }

    @GetMapping(RequestInfo.GROUP_ID + RequestInfo.RECEIVER)
    public List<DeptDto> findAllDeptsWhereReceiver(@PathVariable UUID groupId,@RequestParam String status,
                                                   Authentication authentication) {
        return DtoMapper.convertList(deptService.findAllDeptsByRole(groupId, authentication, false, status), DeptDto.class);
    }

    @DeleteMapping(RequestInfo.DEPT_ID +"/del")
    public void deleteDeptIfNotApproved(@PathVariable UUID deptId,
                                            Authentication authentication) {
        deptService.deleteDept(deptId, authentication);
    }
}
