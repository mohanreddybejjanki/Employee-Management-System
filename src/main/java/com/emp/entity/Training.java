package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "trainings")
@Data
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String trainingName;

    private String provider;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // SCHEDULED, ONGOING, COMPLETED

    private String certification; // YES, NO

    private String remarks;
}