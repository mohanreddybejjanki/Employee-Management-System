package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Data
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String leaveType; // CASUAL, SICK, EARNED, MATERNITY

    private String reason;

    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    private LocalDate appliedOn;

    private String adminComment;

    @PrePersist
    public void prePersist() {
        this.appliedOn = LocalDate.now();
    }
}
