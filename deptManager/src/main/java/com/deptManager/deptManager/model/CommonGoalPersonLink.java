package com.deptManager.deptManager.model;

import com.deptManager.deptManager.model.compositeKeys.CommonGoalPersonLinkKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommonGoalPersonLink {
    @EmbeddedId
    private CommonGoalPersonLinkKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("commonGoalId")
    @JoinColumn(name = "common_goal_id")
    private CommonGoal commonGoal;

    private Boolean approvedBySender;

    private Boolean approvedByReceiver;
}


