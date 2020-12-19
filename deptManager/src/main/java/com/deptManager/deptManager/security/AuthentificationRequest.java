package com.deptManager.deptManager.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthentificationRequest implements Serializable {

    public AuthentificationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private String login;
    private String password;
}
