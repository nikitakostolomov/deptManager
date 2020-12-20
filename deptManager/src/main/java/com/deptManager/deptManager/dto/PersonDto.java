package com.deptManager.deptManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PersonDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String login;

    private String personRole;
}
