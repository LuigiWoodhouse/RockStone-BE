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

    @Column(name = "CreatedAt", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Date entrDate;

}
