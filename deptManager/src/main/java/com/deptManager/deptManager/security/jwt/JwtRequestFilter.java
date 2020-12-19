package com.deptManager.deptManager.security.jwt;


import com.deptManager.deptManager.common.SecurityInfo;
import com.deptManager.deptManager.configuration.SwaggerConfig;
import com.deptManager.deptManager.security.JwtUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserService userDetailsService;

    private final JwtUtil jwtUtil;

    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        for (String patternStr : SwaggerConfig.AUTH_WHITELIST) {
            if (matcher.match(patternStr, uri)) {
                chain.doFilter(request, response);
                return;
            }
        }

        final String authorizationHeader = request.getHeader(SecurityInfo.AUTH);
        String username = null;
        String jwt;

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityInfo.BEARER_AUTH)) {
            jwt = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(request, jwt)) {
                username = jwtUtil.getUsernameFromToken(jwt);
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            request.setAttribute(SecurityInfo.FAILED, SecurityInfo.AUTH + SecurityInfo.CONNECTOR + SecurityInfo.FAILED);
        }
        chain.doFilter(request, response);
    }

}