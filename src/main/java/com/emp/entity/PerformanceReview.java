package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "performance_reviews")
@Data
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private int reviewYear;

    private String reviewPeriod; // Q1, Q2, Q3, Q4, ANNUAL

    private int rating; // 1-5

    private String goals;

    private String achievements;

    private String areasOfImprovement;

    private String adminComments;

    private LocalDate reviewDate;

    private String status; // PENDING, COMPLETED
}