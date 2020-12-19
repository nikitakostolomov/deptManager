package com.deptManager.deptManager.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    private Boolean approvedBySender;

    private Boolean approvedByReceiver;

}
