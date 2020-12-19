package com.deptManager.deptManager.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AuthenticationResponse implements Serializable {

    private final String jwt;

    private List<String> userRole;

    public AuthenticationResponse(String jwt, Collection<? extends GrantedAuthority> userRole) {
        this.jwt = jwt;
        this.userRole = userRole.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
