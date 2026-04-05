package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "overtime")
@Data
public class Overtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate date;

    private double hours;

    private String reason;

    private String status; // PENDING, APPROVED, REJECTED

    private LocalDate appliedOn;

    private String adminComment;

    @PrePersist
    public void prePersist() {
        this.appliedOn = LocalDate.now();
    }
}