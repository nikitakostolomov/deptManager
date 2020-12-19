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
public class CommonGoal {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "groups_id",  foreignKey = @ForeignKey(name = "fk_commonGoal_groups_id"))
    private Groups group;

    private String name;

    private Integer sum;

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    private Person receiver;

    @OneToMany(mappedBy = "commonGoal")
    private List<CommonGoalPersonLink> personLinkList;

}
