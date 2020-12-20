package com.deptManager.deptManager.service;

import com.deptManager.deptManager.common.AuthHelper;
import com.deptManager.deptManager.dto.PersonRegisterDto;
import com.deptManager.deptManager.exceptions.GeneralException;
import com.deptManager.deptManager.model.GroupPersonLink;
import com.deptManager.deptManager.model.Groups;
import com.deptManager.deptManager.model.Person;
import com.deptManager.deptManager.repositories.PersonRepository;
import com.deptManager.deptManager.security.AuthentificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<Person> getByLoginOptional(String login) {
        return personRepository.findByLogin(login);
    }

    public Person getByLogin(String login){
        return getByLoginOptional(login).orElseThrow(() -> new GeneralException("User not found"));
    }

    public Person registerPerson(PersonRegisterDto personRegisterDto) {
        personRepository.findByLogin(personRegisterDto.getLogin()).ifPresent(person -> {
            throw new GeneralException("Login is already in use");
        });
        Person personToRegister = Person.builder()
                .first_name(personRegisterDto.getFirst_name())
                .last_name(personRegisterDto.getLast_name())
                .login(personRegisterDto.getLogin())
                .password(passwordEncoder.encode(personRegisterDto.getPassword()))
                .build();
        return personRepository.save(personToRegister);
    }

    public List<Groups> getAllGroupsOfUser(Authentication authentication){
        String login = AuthHelper.getUsernameFromAuth(authentication);
        Person person = getByLogin(login);
        return person.getGroupsList().stream()
                .map(GroupPersonLink::getGroup)
                .collect(Collectors.toList());

    }
}
