package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "assets")
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;

    private String assetCode;

    private String assetType; // LAPTOP, MOBILE, CHAIR, DESK, OTHER

    private String brand;

    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedTo;

    private LocalDate assignedDate;

    private String condition; // GOOD, DAMAGED, UNDER_REPAIR

    private String status; // ASSIGNED, AVAILABLE, RETIRED

    private String remarks;
}