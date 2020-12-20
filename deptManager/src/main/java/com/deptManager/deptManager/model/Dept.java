package com.deptManager.deptManager.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Person payer;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Person receiver;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Integer amount;

    private String comment;

    @Builder.Default
    private Boolean approvedBySender = false;

    @Builder.Default
    private Boolean approvedByReceiver = false;

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "groups_id",  foreignKey = @ForeignKey(name = "fk_dept_groups_id"))
    private Groups group;

    private Status deptStatus;

}
