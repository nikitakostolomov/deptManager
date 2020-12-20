package com.deptManager.deptManager.model.compositeKeys;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommonGoalPersonLinkKey implements Serializable {

    private UUID personId;

    private UUID commonGoalId;
}