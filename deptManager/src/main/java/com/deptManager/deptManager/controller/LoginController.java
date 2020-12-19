package com.deptManager.deptManager.controller;


import com.deptManager.deptManager.common.RequestInfo;
import com.deptManager.deptManager.security.AuthentificationRequest;
import com.deptManager.deptManager.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(RequestInfo.LOGIN)
@Profile("security")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody AuthentificationRequest authenticationRequest) throws Exception {
        return loginService.generateToken(authenticationRequest);
    }


}

