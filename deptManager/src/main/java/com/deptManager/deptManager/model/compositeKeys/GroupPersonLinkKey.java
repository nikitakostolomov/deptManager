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
public class GroupPersonLinkKey implements Serializable {

    private UUID person_id;

    private UUID group_id;
}

