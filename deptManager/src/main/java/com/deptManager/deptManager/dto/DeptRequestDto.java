package com.deptManager.deptManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeptRequestDto {

    private Integer amount;

    private String comment;

    private UUID payerId;

}
