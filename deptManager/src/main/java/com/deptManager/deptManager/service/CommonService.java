package com.deptManager.deptManager.service;

import com.deptManager.deptManager.exceptions.GeneralException;
import com.deptManager.deptManager.model.Person;
import com.deptManager.deptManager.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonService {

    private final PersonRepository personRepository;

    public Person getPersonFromContext(Authentication authentication) {
        String usernameFromAuth = getUsernameFromAuth(authentication);
        Person requester = personRepository.findByLogin(usernameFromAuth)
                .orElseThrow(()-> new GeneralException("Person not found"));
        return requester;
    }

    private String getUsernameFromAuth(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();

    }
}
