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
    private List<CommonGoal> commonGoals;

    @OneToMany(mappedBy = "group")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<GroupPersonLink> participantsList = new ArrayList<>();

}
