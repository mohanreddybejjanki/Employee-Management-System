package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "exit_requests")
@Data
public class ExitRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate resignationDate;

    private LocalDate lastWorkingDate;

    private String reason;

    private String status; // PENDING, ACCEPTED, REJECTED

    private String adminComment;

    private String noticePeriod; // e.g. "30 days", "60 days"
}