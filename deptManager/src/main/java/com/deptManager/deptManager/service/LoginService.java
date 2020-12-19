package com.deptManager.deptManager.service;

import com.deptManager.deptManager.exceptions.GeneralException;
import com.deptManager.deptManager.model.Person;
import com.deptManager.deptManager.security.AuthenticationResponse;
import com.deptManager.deptManager.security.AuthentificationRequest;
import com.deptManager.deptManager.security.JwtUserService;
import com.deptManager.deptManager.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile("security")
public class LoginService {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final JwtUserService jwtUserService;

    public ResponseEntity<?> generateToken(AuthentificationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new GeneralException("WRONG PASSWORD");
        }
        final UserDetails userDetails = jwtUserService.loadUserByUsername(authenticationRequest.getLogin());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getAuthorities()));
    }
}

