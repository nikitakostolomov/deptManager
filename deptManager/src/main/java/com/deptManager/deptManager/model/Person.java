package com.deptManager.deptManager.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String first_name;

    private String last_name;

    private String password;

    private String login;

    private Boolean isAdmin;

    @OneToMany(mappedBy = "person")
    private List<GroupPersonLink> groupsList;

}
