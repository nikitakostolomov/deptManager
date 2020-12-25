package com.deptManager.deptManager.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Groups {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<GroupPersonLink> participantsList = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Dept> depts;

}
