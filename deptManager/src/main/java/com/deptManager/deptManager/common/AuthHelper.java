package com.deptManager.deptManager.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthHelper {

    public static String getUsernameFromAuth(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return username;
    }
}
