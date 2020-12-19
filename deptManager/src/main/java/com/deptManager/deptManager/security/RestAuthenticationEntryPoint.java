package com.deptManager.deptManager.security;

import com.deptManager.deptManager.common.SecurityInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final String failed = (String) request.getAttribute(SecurityInfo.FAILED);
        if (failed != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityInfo.UNAUTHORIZED);
        }
    }
}