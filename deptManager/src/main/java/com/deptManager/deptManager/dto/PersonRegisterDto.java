package com.deptManager.deptManager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRegisterDto {
    @JsonProperty("name")
    private String first_name;

    @JsonProperty("surname")
    private String last_name;

    private String password;

    private String login;
}
