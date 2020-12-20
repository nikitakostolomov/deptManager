package com.deptManager.deptManager.model;

import com.deptManager.deptManager.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DeptsShorten {

    private PersonDto personDto;

    private List<UUID> deptIds;

    private Integer sum;

    private UUID groupId;
}
