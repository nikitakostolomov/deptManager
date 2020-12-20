package com.deptManager.deptManager.model;

import com.deptManager.deptManager.model.compositeKeys.GroupPersonLinkKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GroupPersonLink {
    @EmbeddedId
    private GroupPersonLinkKey id = new GroupPersonLinkKey();

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Groups group;

    private PersonRole personRole;
}
