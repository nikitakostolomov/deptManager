package com.deptManager.deptManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DeptDto {
    private UUID id;

    private PersonDto payer;

    private PersonDto receiver;

    private LocalDateTime createdAt;

    private Integer amount;

    private String comment;

    private Boolean approvedBySender;

    private Boolean approvedByReceiver;

    private UUID groupId;

    private String groupName;

}
