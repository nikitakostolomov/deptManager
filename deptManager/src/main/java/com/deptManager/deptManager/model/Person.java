package com.deptManager.deptManager.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Person {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String firstName;

    private String lastName;

    private String password;

    private String login;

    private Boolean isAdmin;

    @OneToMany(mappedBy = "person")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<GroupPersonLink> groupsList = new ArrayList<>();

}
