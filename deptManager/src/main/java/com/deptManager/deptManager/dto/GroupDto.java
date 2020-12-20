package com.deptManager.deptManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GroupDto {
    private UUID id;

    private String name;

    private List<GroupPersonLinkDto> participantsList;
}
