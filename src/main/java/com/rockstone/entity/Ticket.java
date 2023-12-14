package com.rockstone.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @NotNull
    @Column(name="message")
    private String message;

    @NotNull
    @Column(name="category")
    private String category;

    @NotNull
    @Column(name="agentAssigned")
    private String agentAssigned;

    @NotNull
    @Column(name="status")
    private String status;

    @NotNull
    @Column(name="resolution")
    private String resolution;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private Date createdAt;

}