package com.rockstone.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String message;

    @NotNull
    private String category;

    @NotNull
    private String agentAssigned;

    @NotNull
    private String status;

    @NotNull
    private String resolution;

    @Column(name = "CreatedAt", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
