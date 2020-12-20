package com.deptManager.deptManager.controller;

import com.deptManager.deptManager.common.DtoMapper;
import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.dto.DeptDto;
import com.deptManager.deptManager.dto.DeptRequestDto;
import com.deptManager.deptManager.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.DEPT)
public class DeptController {

    private final DeptService deptService;

    @PostMapping(RequestInfo.GROUP_ID)
    public DeptDto requestDept(@PathVariable UUID groupId, @RequestBody DeptRequestDto deptRequestDto,
                               Authentication authentication){
        return DtoMapper.convertToClass(deptService.requestDept(groupId, deptRequestDto, authentication), DeptDto.class);
    }

    @PostMapping(RequestInfo.DEPT_ID + RequestInfo.APPROVE + RequestInfo.PAYER)
    public DeptDto approveDeptAsPayer(@PathVariable UUID deptId, Authentication authentication){
        return DtoMapper.convertToClass(deptService.approveDeptAsPayer(deptId, authentication), DeptDto.class);
    }

    @PostMapping(RequestInfo.DEPT_ID + RequestInfo.APPROVE + RequestInfo.RECEIVER)
    public DeptDto approveDeptAsReceiver(@PathVariable UUID deptId, Authentication authentication){
        return DtoMapper.convertToClass(deptService.approveDeptAsReceiver(deptId, authentication), DeptDto.class);
    }
}
